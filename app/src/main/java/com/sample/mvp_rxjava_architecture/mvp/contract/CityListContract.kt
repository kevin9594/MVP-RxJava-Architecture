package com.sample.mvp_rxjava_architecture.mvp.contract

import com.sample.mvp_rxjava_architecture.base.BaseBean
import com.sample.mvp_rxjava_architecture.base.BaseContract
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import io.reactivex.rxjava3.core.Single

interface CityListContract {

    interface View : BaseContract.View {
        fun success(dataBeanList: MutableList<CityListBean>)
        fun fail(message: String)
    }


    interface Model {
        fun getData(): Single<BaseBean<MutableList<CityListBean>>>
    }


    interface OnCallBack {
        fun onSuccess(dataBeanList: MutableList<CityListBean>)
        fun onFail(message: String)
    }


}