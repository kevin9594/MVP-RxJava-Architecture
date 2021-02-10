package com.sample.mvp_rxjava_architecture.mvp.fragment

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.adapter.CityListAdapter
import com.sample.mvp_rxjava_architecture.base.BaseFragment
import com.sample.mvp_rxjava_architecture.base.BasePresenterFragment
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import com.sample.mvp_rxjava_architecture.mvp.contract.CityListContract
import com.sample.mvp_rxjava_architecture.mvp.presenter.CityListPresenter
import kotlinx.android.synthetic.main.fragment_city_list.*

class CityListFragment : BasePresenterFragment<CityListContract.View, CityListPresenter>(), CityListContract.View, CityListAdapter.Listener {

    companion object {
        const val title = R.string.tab_title_city_list
    }

    private var topAdapter = CityListAdapter(this)

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

        v_click.setOnClickListener {
            Toast.makeText(requireContext(), topAdapter.cityList[0].provinceId, Toast.LENGTH_SHORT).show()
        }

        v_refresh.setOnClickListener {

            for (i in topAdapter.cityList.indices){
                if(i == 0 || i == 2)
                topAdapter.cityList[i].change = true
            }

            topAdapter.notifyDataSetChanged()

        }

    }

    private fun setAdapter() {
        rv_city_list.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = topAdapter
            val itemDecoration = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(getDrawable(requireActivity(), R.drawable.divider)!!)
            addItemDecoration(itemDecoration)
        }
    }


    override fun onCreatePresenter(): CityListPresenter {
        return CityListPresenter()
    }


    private var defaultList: MutableList<CityListBean> = mutableListOf()
    override fun success(dataBeanList: MutableList<CityListBean>) {
        dataBeanList.let {
            topAdapter.cityList = it
        }
    }


    override fun fail(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun save() {

        /*保存至repository*/
        defaultList = topAdapter.cityList

        defaultList.forEach {
            Log.d("[test]", " => ${it.input}")
        }


    }

}