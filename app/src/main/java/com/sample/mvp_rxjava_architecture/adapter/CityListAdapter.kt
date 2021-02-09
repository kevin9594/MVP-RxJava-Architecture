package com.sample.mvp_rxjava_architecture.adapter

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.bean.CityListBean

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {


    var cityList: MutableList<CityListBean> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false))
    }


    override fun getItemCount(): Int {
        return cityList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(cityList[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCity = itemView.findViewById<TextView>(R.id.tv_city)!!
        private val etInput = itemView.findViewById<EditText>(R.id.et_input)!!

        fun bindModel(cityListBean: CityListBean) {

            if (etInput.tag is TextWatcher) {
                etInput.removeTextChangedListener(etInput.tag as TextWatcher)
            }

            etInput.setText(cityListBean.provinceId)

            tvCity.text = cityListBean.city

            val tw = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (TextUtils.isEmpty(s.toString())) {
                        cityListBean.provinceId = ""
                    } else {
                        cityListBean.provinceId = s.toString()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            }

            etInput.addTextChangedListener(tw)
            etInput.tag = tw
        }
    }
}


