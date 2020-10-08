package com.sample.mvp_rxjava_architecture.mvp.contract

import com.sample.mvp_rxjava_architecture.base.BaseBean
import com.sample.mvp_rxjava_architecture.base.BaseContract
import com.sample.mvp_rxjava_architecture.bean.SignBean
import io.reactivex.rxjava3.core.Single

interface SignContract {

    interface View : BaseContract.View {
        fun success(signBean: SignBean)
        fun fail(message: String)
    }


    interface Model {
        fun signIn(account: String, password: String): Single<BaseBean<SignBean>>
    }


    interface OnCallBack {
        fun onSuccess(signBean: SignBean)
        fun onFail(message: String)
    }

}