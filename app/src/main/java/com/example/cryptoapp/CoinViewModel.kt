package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.api.apiFactory
import com.example.cryptoapp.database.Appdatabase
import com.example.cryptoapp.pojo.CoinClassInfoRawData
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Appdatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fsym:String):LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fsym)
    }

   private fun loadData() {
        val disposable = apiFactory.apiService.getTopCooinsInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { apiFactory.apiService.getFullPriceList(fSyms = it.toString()) }
            .map{getPriceListFromRawData(it)}
            .delaySubscription(10,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io()).subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("Testofupd","Success: " + it.toString())
            }, {
                it.message?.let { it1 -> Log.d("Testofupd", "Failure: $it1") }
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinClassInfoRawData: CoinClassInfoRawData): List<CoinPriceInfo>? {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinClassInfoRawData.coinPriceInfojsObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeyset = currencyJson.keySet()
            for (currencyKey in currencyKeyset) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}