package com.sample.mvp_rxjava_architecture.manager

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.mvp_rxjava_architecture.BuildConfig
import com.sample.mvp_rxjava_architecture.interceptor.LogInterceptor
import com.sample.mvp_rxjava_architecture.interceptor.MockApiInterceptor
import com.sample.mvp_rxjava_architecture.interceptor.RequestInterceptor
import com.sample.mvp_rxjava_architecture.util.SSLSocketFactoryUtils
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager


class RequestManager private constructor(context: Context) {

    var retrofit: Retrofit

    companion object {
        const val CONNECT_TIMEOUT: Long = 15 * 1000
        const val WRITE_TIMEOUT: Long = 15 * 1000
        const val READ_TIMEOUT: Long = 15 * 1000

        private var instance: RequestManager? = null
        private var staticContext: Context? = null

        fun init(c: Context) {
            staticContext = c
        }

        fun getInstance(): RequestManager {
            if (staticContext == null) {
                throw RuntimeException("You must initialize this manager before getting instance")
            }
            if (instance == null) {
                instance = RequestManager(staticContext!!)
            }
            return instance!!
        }
    }

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .sslSocketFactory(
                SSLSocketFactoryUtils.getSSLSocketFactory(),
                SSLSocketFactoryUtils.getTrustManager()[0] as X509TrustManager
            )
            .hostnameVerifier(SSLSocketFactoryUtils.getHostnameVerifier())
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor(context))

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(LogInterceptor().setLevel(LogInterceptor.Level.BODY))
        }

        // mock data, 必須擺在最後
        if (BuildConfig.MOCK) {
            okHttpClientBuilder.addInterceptor(MockApiInterceptor(context))
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }


}
