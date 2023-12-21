package com.example.nt118_project.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nt118_project.Model.Hotel
import com.example.nt118_project.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class HotelAdapter(private var dataList: ArrayList<Hotel>, private var context: Context): RecyclerView.Adapter<HotelAdapter.HotelViewHolder>(){
    public var onItemClick: ((Hotel) -> Unit)? = null
    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tVName: TextView = itemView.findViewById<TextView>(R.id.tv_name_hotel)
        var tvAddress: TextView = itemView.findViewById<TextView>(R.id.tv_address)
        var tVPrice: TextView = itemView.findViewById<TextView>(R.id.price)
        var Ratingbar: RatingBar = itemView.findViewById<RatingBar>(R.id.rating_bar)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HotelAdapter.HotelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.detail_list_of_hotels,parent,false)
        return HotelViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HotelAdapter.HotelViewHolder, position: Int) {
        val currItem: Hotel = dataList[position]


        holder.tVName.setText(currItem.Name)
        holder.tvAddress.setText((currItem.Address))
        holder.tVPrice.setText(formatter(currItem.Price) + " VND")
        holder.Ratingbar.numStars = currItem.Rating
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currItem)
        }
    }
    fun formatter(n: Int) =
        DecimalFormat("#,###")
            .format(n)
            .replace(",", ".")
}