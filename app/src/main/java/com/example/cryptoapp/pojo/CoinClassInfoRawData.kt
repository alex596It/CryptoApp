package com.example.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class CoinClassInfoRawData (
    @SerializedName("RAW")
    @Expose
    val coinPriceInfojsObject:JsonObject? = null

)