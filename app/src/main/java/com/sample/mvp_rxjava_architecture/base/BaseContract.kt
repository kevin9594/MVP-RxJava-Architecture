package com.sample.mvp_rxjava_architecture.base

interface BaseContract {

    interface View {
        fun showProgressDialog()
        fun dismissProgressDialog()
        fun requestSignInAndGoToSignIn(message: String, isFinish: Boolean)
        fun showToast(id: Int)
        fun showToast(message: String)
    }

}