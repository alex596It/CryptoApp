package com.example.cryptoapp.api

import com.example.cryptoapp.pojo.CoinClassInfoRawData
import com.example.cryptoapp.pojo.CoinInfoListData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface apiService {
    @GET("top/totalvolfull")
    fun getTopCooinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey:String = "",
        @Query(QUERY_PARAM_LIMIT) limit:Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym:String = "USD"
    ):Single<CoinInfoListData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey:String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms:String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms:String = "USD"
        ):Single<CoinClassInfoRawData>


    companion object{
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
    }
}