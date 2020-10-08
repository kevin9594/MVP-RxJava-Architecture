package com.sample.mvp_rxjava_architecture.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.bean.CityListBean

class CityListAdapter() : RecyclerView.Adapter<CityListAdapter.ViewHolder>()  {

    var context: Context? = null
    var cityListBean: MutableList<CityListBean>? = null

    constructor(context: Context, cityListBean: MutableList<CityListBean>) : this() {
        this.context = context
        this.cityListBean = cityListBean
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false))
    }


    override fun getItemCount(): Int {
       return cityListBean!!.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(cityListBean!![position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_city = itemView.findViewById<TextView>(R.id.tv_city)!!

        fun bindModel(cityListBean: CityListBean) {
            tv_city.text = cityListBean.city
        }
    }


}