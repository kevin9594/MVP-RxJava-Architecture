package com.sample.mvp_rxjava_architecture.base

import com.sample.mvp_rxjava_architecture.R
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BasePresenter<V> {

    var view: V? = null

    var disposables: CompositeDisposable = CompositeDisposable()


    open fun attachView(view: V) {
        this.view = view
    }


    open fun detachView() {
        this.view = null
        this.disposables.dispose()
    }


    fun isAttach(): Boolean {
        return view != null
    }


    fun requestSignIn(message: String) {
        view?.let {
            val v = (it as BaseContract.View)
            v.dismissProgressDialog()
            v.requestSignInAndGoToSignIn(message, true)
        }
    }


    fun onNetworkUnavailable() {
        view?.let {
            (it as BaseContract.View).showToast(R.string.toast_network_is_unavailable)
        }
    }
}