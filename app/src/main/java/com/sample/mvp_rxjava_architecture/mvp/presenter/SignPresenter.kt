package com.sample.mvp_rxjava_architecture.mvp.presenter

import com.sample.mvp_rxjava_architecture.base.BasePresenter
import com.sample.mvp_rxjava_architecture.bean.SignBean
import com.sample.mvp_rxjava_architecture.manager.NetworkStatusManager
import com.sample.mvp_rxjava_architecture.mvp.contract.SignContract
import com.sample.mvp_rxjava_architecture.mvp.model.SignModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class SignPresenter : BasePresenter<SignContract.View>(), SignContract.OnCallBack {

    private val signModel = SignModel()

    fun getData(account: String, password: String) {

        if (NetworkStatusManager.getInstance().isUnavailable()) {
            this.onNetworkUnavailable()
            return
        }

        disposables.add(

            signModel.signIn(account, password)
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

    override fun onSuccess(signBean: SignBean) {
        view?.success(signBean)
    }

    override fun onFail(message: String) {
        view?.fail(message)
    }


}