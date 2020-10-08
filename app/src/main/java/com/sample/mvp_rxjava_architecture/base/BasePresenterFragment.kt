package com.sample.mvp_rxjava_architecture.base

import android.os.Bundle
import android.view.View

abstract class BasePresenterFragment<V, T : BasePresenter<V>> : BaseFragment() {
    companion object {
        val TAG = BasePresenterFragment::class.java.simpleName
    }

    var mPresenter: T? = null

    abstract fun onCreatePresenter(): T

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = onCreatePresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
    }
}