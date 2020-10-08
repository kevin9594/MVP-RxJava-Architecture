package com.sample.mvp_rxjava_architecture.mvp.model

import com.sample.mvp_rxjava_architecture.api.SignApi
import com.sample.mvp_rxjava_architecture.base.BaseBean
import com.sample.mvp_rxjava_architecture.bean.SignBean
import com.sample.mvp_rxjava_architecture.manager.RequestManager
import com.sample.mvp_rxjava_architecture.mvp.contract.SignContract
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SignModel : SignContract.Model {

    private val signApi : SignApi = RequestManager
        .getInstance()
        .retrofit
        .create(SignApi::class.java)


    override fun signIn(account: String, password: String): Single<BaseBean<SignBean>> {
      return signApi.signIn(SignApi.signIn(account, password)).subscribeOn(Schedulers.io())
    }
}