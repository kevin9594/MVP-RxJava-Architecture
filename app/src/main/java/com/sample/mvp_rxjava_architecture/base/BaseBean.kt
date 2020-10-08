package com.sample.mvp_rxjava_architecture.base

class BaseBean<T> {
    var code = 0
    var message: String? = null
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }

    override fun toString(): String {
        return "BaseBean(code=$code, message=$message, data=$data)"
    }

}