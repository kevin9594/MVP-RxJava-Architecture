package com.sample.mvp_rxjava_architecture.interceptor

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

class RequestInterceptor(private val context: Context?) : Interceptor {

    companion object {
        val TAG = RequestInterceptor::class.java.simpleName
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (context == null) {
            throw NullPointerException("Please call RequestManager.getInstance().init(context) first")
        }
        val request = chain.request()
        val builder = request.newBuilder()
        val urlBuilder = request.url.newBuilder()

        // adds the pre-encoded query parameter to this URL's query string
        // urlBuilder.addEncodedQueryParameter("encoded", "qazwsx")

        // encodes the query parameter using UTF-8 and adds it to this URL's query string
        // urlBuilder.addQueryParameter("haha", "good")

        // header
        // ex : builder.addHeader("appKey", BuildConfig.APP_KEY)


        val httpUrl = urlBuilder.build()
        val newRequest = builder.url(httpUrl).build()

        return try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            Log.e(TAG, "intercept Exception:$e")
            chain.proceed(request)
        }

    }

}
