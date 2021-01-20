package com.sample.mvp_rxjava_architecture.mvp.fragment

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.adapter.CityListAdapter
import com.sample.mvp_rxjava_architecture.base.BaseFragment
import com.sample.mvp_rxjava_architecture.base.BasePresenterFragment
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import com.sample.mvp_rxjava_architecture.mvp.contract.CityListContract
import com.sample.mvp_rxjava_architecture.mvp.presenter.CityListPresenter
import kotlinx.android.synthetic.main.fragment_city_list.*

class CityListFragment : BasePresenterFragment<CityListContract.View, CityListPresenter>(),
    CityListContract.View {

    companion object {

        var title = R.string.tab_title_city_list

    }

    private var cityListBean: MutableList<CityListBean> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        mPresenter?.getData()
    }

    private fun setAdapter() {
        rv_city_list.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = CityListAdapter(requireActivity(), cityListBean)
        }

        rv_city_list2.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = CityListAdapter(requireActivity(), cityListBean)
        }
    }


    override fun onCreatePresenter(): CityListPresenter {
        return CityListPresenter()
    }


    override fun success(dataBeanList: MutableList<CityListBean>) {
        if (dataBeanList.isNotEmpty()) {
            Log.d("[debug]", "${dataBeanList.size}")
            cityListBean.addAll(dataBeanList)
        }

        rv_city_list.adapter?.notifyDataSetChanged()

        rv_city_list2.adapter?.notifyDataSetChanged()
    }


    override fun fail(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}