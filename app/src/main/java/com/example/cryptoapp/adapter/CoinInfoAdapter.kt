package com.example.cryptoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinInfo
import com.example.cryptoapp.pojo.CoinInfoListData
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter: RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList:List<CoinPriceInfo> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var onCoinClickListener: OnCoinClickListener ?= null

    inner class CoinInfoViewHolder(itemView: View): ViewHolder(itemView){
        var imageView: ImageView = itemView.findViewById(R.id.imageCoin)
        var textViewSymv: TextView = itemView.findViewById(R.id.textViewSymbols)
        var textViewDate: TextView = itemView.findViewById(R.id.DateTextView)
        var textViewPrice: TextView = itemView.findViewById(R.id.count)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cryptoi_nfo, parent,false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            textViewSymv.text = coin.fromsymbol + " / " + coin.tosymbol
            textViewPrice.text = coin.price.toString()
            textViewDate.text = "Время последнего обновления: " + coin.getFormattedTime()
            Picasso.get().load(coin.getFullImageURL()).into(imageView)
        }
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    override fun getItemCount(): Int {
        return coinInfoList.size
    }

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}