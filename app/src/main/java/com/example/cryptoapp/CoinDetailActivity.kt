package com.example.cryptoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var imageView:ImageView
    private lateinit var fromm:TextView
    private lateinit var toott:TextView
    private lateinit var maxPrice:TextView
    private lateinit var maxPriceNumber:TextView
    private lateinit var minPrice:TextView
    private lateinit var minPriceNumber:TextView
    private lateinit var updatea:TextView
    private lateinit var updateNumb:TextView
    private lateinit var price:TextView
    private lateinit var priceNumb:TextView
    private lateinit var lastt:TextView
    private lateinit var lastnumb:TextView

    private lateinit var viewModel:CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        imageView = findViewById(R.id.imageView2)
        fromm = findViewById(R.id.textViewFromsynv)
        toott = findViewById(R.id.textView3)
        maxPriceNumber = findViewById(R.id.maxPriceNumb)
        minPriceNumber = findViewById(R.id.minPriceNumb2)
        updatea = findViewById(R.id.updateNumber)
        price = findViewById(R.id.NumbPrice)
        lastt = findViewById(R.id.LastNumber)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[(CoinViewModel::class.java)]
        viewModel.getDetailInfo(fromSymbol.toString()).observe(this, Observer {
            fromm.text = it.fromsymbol
            toott.text = it.tosymbol
            maxPriceNumber.text = it.highday.toString()
            minPriceNumber.text = it.lowday.toString()
            lastt.text = it.lastmarket
            price.text = it.price.toString()
            updatea.text = it.getFormattedTime()
            Picasso.get().load(it.getFullImageURL()).into(imageView)
        })
    }

    companion object {

        private const val EXTRA_FROM_SYMBOL = "fsym"


        fun newIntent(context: Context, fromsymbol: String):Intent{
        val intent = Intent(context,CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromsymbol)
            return intent
        }

    }
}