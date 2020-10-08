package com.sample.mvp_rxjava_architecture.api

import com.sample.mvp_rxjava_architecture.base.BaseBean
import com.sample.mvp_rxjava_architecture.bean.SignBean
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST

interface SignApi {

    companion object {

        const val SIGN_IN = "api/sign-in"
        fun signIn(account: String, password: String): RequestBody {
            val requestData = JSONObject()
            try {
                requestData.put("account", account)
                requestData.put("passWord", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return requestData.toString().toRequestBody("application/json".toMediaType())
        }

    }

    @POST(SIGN_IN)
    fun signIn(@Body requestBody: RequestBody): Single<BaseBean<SignBean>>


}