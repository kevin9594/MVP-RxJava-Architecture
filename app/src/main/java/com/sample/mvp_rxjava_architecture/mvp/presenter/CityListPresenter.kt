package com.sample.mvp_rxjava_architecture.mvp.presenter

import com.sample.mvp_rxjava_architecture.base.BasePresenter
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import com.sample.mvp_rxjava_architecture.manager.NetworkStatusManager
import com.sample.mvp_rxjava_architecture.mvp.contract.CityListContract
import com.sample.mvp_rxjava_architecture.mvp.model.CityListModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class CityListPresenter : BasePresenter<CityListContract.View>(), CityListContract.OnCallBack {

    private var cityListModel: CityListModel = CityListModel()

    fun getData() {

        if (NetworkStatusManager.getInstance().isUnavailable()) {
            this.onNetworkUnavailable()
            return
        }

        disposables.add(

            cityListModel.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                        if (it != null) {

                            when (it.code) {

                                0 -> onSuccess(it.data!!)

                                1 -> onFail(it.message!!)

                                else -> onFail("未知錯誤")

                            }

                        }

                    }, {
                        onFail("未知錯誤")
                    }

                )

        )

    }

    override fun onSuccess(dataBeanList: MutableList<CityListBean>) {
        view?.success(dataBeanList)
    }

    override fun onFail(message: String) {
        view?.fail(message)
    }
}