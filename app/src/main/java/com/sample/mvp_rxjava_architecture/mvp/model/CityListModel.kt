package com.sample.mvp_rxjava_architecture.mvp.model

import com.sample.mvp_rxjava_architecture.api.CityListApi
import com.sample.mvp_rxjava_architecture.base.BaseBean
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import com.sample.mvp_rxjava_architecture.manager.RequestManager
import com.sample.mvp_rxjava_architecture.mvp.contract.CityListContract
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class CityListModel: CityListContract.Model {

    private val cityListApi : CityListApi = RequestManager
        .getInstance()
        .retrofit
        .create(CityListApi::class.java)

    override fun getData(): Single<BaseBean<MutableList<CityListBean>>> {
        return cityListApi.cityList().subscribeOn(Schedulers.io())
    }


}