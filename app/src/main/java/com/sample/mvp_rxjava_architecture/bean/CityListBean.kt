package com.sample.mvp_rxjava_architecture.bean

data class CityListBean(
    /**
     * cityId : 02
     * city : 高雄市
     * provinceId : 100
     * areas : null
     */
    var cityId: String? = null,
    var city: String? = null,
    var provinceId: String? = null,
    var areas: Any? = null
) {
    var input: String? = null
    var change: Boolean = false
    var status: Status = Status.SAME
}

enum class Status{
    SMALLER, SAME, LARGER
}