package com.sample.mvp_rxjava_architecture.adapter

import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import com.sample.mvp_rxjava_architecture.bean.Status

class CityListAdapter(private val listener: Listener) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {


    var cityList: MutableList<CityListBean> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var focusPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false))
    }


    override fun getItemCount(): Int {
        return cityList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(cityList[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCity = itemView.findViewById<TextView>(R.id.tv_city)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tv_status)
        private val tvButton = itemView.findViewById<TextView>(R.id.tv_button)
        private val tvWarning = itemView.findViewById<TextView>(R.id.tv_warning)
        private val etInput = itemView.findViewById<EditText>(R.id.et_input)

        private fun check(input: String, cityListBean: CityListBean) {

            if (TextUtils.isEmpty(input)) {

                etInput.setBackgroundResource(R.drawable.select_background)
                tvButton.isEnabled = true

            } else if (input.toInt() >= 100 || input.toInt() <= 9) {

                etInput.setBackgroundResource(R.drawable.border_error)
                tvButton.isEnabled = false

            } else {

                etInput.setBackgroundResource(R.drawable.select_background)
                tvButton.isEnabled = true

            }

        }

        fun bindModel(cityListBean: CityListBean, position: Int) {

            /**
             * 移除輸入監聽
             * 載入各欄位
             * 設定初始狀態
             * 加入輸入監聽 (輸入內容帶入LiveData)
             **/

            /*移除輸入、焦點監聽*/
            if (etInput.tag is TextWatcher) {
                etInput.removeTextChangedListener(etInput.tag as TextWatcher)
            }
            etInput.onFocusChangeListener = null

            /*載入各欄位 初始狀態*/
            tvCity.text = cityListBean.city
            tvButton.setBackgroundResource(R.color.select_button)
            tvWarning.visibility = View.GONE
            tvStatus.setBackgroundColor(0)
            if (cityListBean.status == Status.LARGER || cityListBean.status == Status.SMALLER) {
                tvButton.text = "變動後送出"
            } else {
                tvButton.text = "送出"
            }


            /*先載入輸入內容 再判斷各原件狀態*/
            etInput.setText(cityListBean.input)
            check(etInput.text.toString(), cityListBean)

            /*判斷輸入框是否是當前焦點*/
            if (position == focusPosition) {
                etInput.requestFocus()
                etInput.setSelection(etInput.text.length)
            }

            /*監聽設定*/
            val tw = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    check(s.toString(), cityListBean)
                    if (TextUtils.isEmpty(s.toString())) {
                        cityListBean.input = ""
                    } else {
                        cityListBean.input = s.toString()
                    }
                    listener.save()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }

            val fc = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    etInput.requestFocus()
                    focusPosition = position
                } else {
                    etInput.clearFocus()
                }
            }

            etInput.onFocusChangeListener = fc
            etInput.addTextChangedListener(tw)
            etInput.tag = tw


            /*即時更新判斷*/
            if (cityListBean.change) {
                //復原狀態
                cityListBean.change = false

                //暫時變更UI
                tvButton.setBackgroundColor(itemView.context.getColor(R.color.red))
                tvWarning.visibility = View.VISIBLE
                when (cityListBean.status) {

                    Status.LARGER -> {
                        tvStatus.setBackgroundColor(itemView.context.getColor(R.color.green))
                    }
                    Status.SMALLER -> {
                        tvStatus.setBackgroundColor(itemView.context.getColor(R.color.red))
                    }
                    else -> {
                    }
                }

                //三秒後復原UI
                Handler().postDelayed({
                    tvButton.setBackgroundResource(R.color.select_button)
                    tvWarning.visibility = View.GONE
                    tvStatus.setBackgroundColor(0)
                }, 3000)
            }


            //test
            tvButton.setOnClickListener {
                Toast.makeText(itemView.context, "click ${etInput.text}", Toast.LENGTH_SHORT).show()
            }

        }
    }


    interface Listener {
        fun save()
    }


}


