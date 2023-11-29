package com.example.cryptoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class Appdatabase: RoomDatabase(){
    companion object{
        private var db: Appdatabase? = null
        private const val Db_name = "name.db"
        private val Lock = Any()

        fun getInstance(context: Context):Appdatabase{
            synchronized(Lock) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        Appdatabase::class.java,
                        Db_name
                    ).build()
                db = instance
                return instance
            }
            }
        }
    abstract fun coinPriceInfoDao(): CoinPriceInfoDAO
    }
