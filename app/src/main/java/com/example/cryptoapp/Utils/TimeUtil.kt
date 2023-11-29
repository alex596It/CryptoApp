package com.example.cryptoapp.Utils

import java.sql.Date
import java.sql.SQLRecoverableException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(timestamp: Int?):String{
    if (timestamp == null) return ""
    val  stamp = Timestamp((timestamp * 1000).toLong())
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"
    val sdm = SimpleDateFormat(pattern, Locale.getDefault())
    sdm.timeZone = TimeZone.getDefault()
    return sdm.format(date)
}