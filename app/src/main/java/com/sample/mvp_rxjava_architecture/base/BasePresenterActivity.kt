package com.sample.mvp_rxjava_architecture.base

import android.annotation.SuppressLint
import android.os.Bundle
import com.sample.mvp_rxjava_architecture.base.BaseActivity

@Suppress("UNCHECKED_CAST")
@SuppressLint("Registered")
abstract class BasePresenterActivity<V, T : BasePresenter<V>> : BaseActivity() {

    var mPresenter: T? = null

    abstract fun onCreatePresenter(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = onCreatePresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

}