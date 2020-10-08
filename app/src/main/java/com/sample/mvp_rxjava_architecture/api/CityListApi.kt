package com.sample.mvp_rxjava_architecture.api

import com.sample.mvp_rxjava_architecture.base.BaseBean
import com.sample.mvp_rxjava_architecture.bean.CityListBean
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST

interface CityListApi {

    companion object {

        const val URL_CITY_LIST = "api/get-city-list"

    }

    @POST(URL_CITY_LIST)
    fun cityList(): Single<BaseBean<MutableList<CityListBean>>>

}