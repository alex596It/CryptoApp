package com.example.cryptoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.adapter.CoinInfoAdapter
import com.example.cryptoapp.pojo.CoinPriceInfo

class MainActivity : AppCompatActivity() {

    private lateinit var CoinPriceList:RecyclerView
    private lateinit var viewModel:CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter =CoinInfoAdapter()
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(this@MainActivity,coinPriceInfo.fromsymbol)
                startActivity(intent)
            }
        }
        CoinPriceList = findViewById(R.id.rvCoinPriceList)
        CoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[(CoinViewModel::class.java)]
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })
    }


}