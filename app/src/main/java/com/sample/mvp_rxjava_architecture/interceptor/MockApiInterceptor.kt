package com.sample.mvp_rxjava_architecture.interceptor

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.sample.mvp_rxjava_architecture.BuildConfig
import com.sample.mvp_rxjava_architecture.api.CityListApi
import com.sample.mvp_rxjava_architecture.api.SignApi
import com.sample.mvp_rxjava_architecture.constant.SampleConstant.ASSET_DIR_MOCK_API
import com.sample.mvp_rxjava_architecture.util.FileUtil.readStringFromInputStream
import com.sample.mvp_rxjava_architecture.util.JsonMapUtil
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.json.JSONObject
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

class MockApiInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response?
        val path = chain.request().url.toUri().path
        val query =
            splitQuery(chain.request().url.toUrl())
        val body = splitBody(chain.request().body)
        response = interceptRequestWhenDebug(chain, path, query, body)
        if (response == null) {
            response = chain.proceed(chain.request())
        }
        return response
    }


    private fun splitBody(body: RequestBody?): Map<String?, Any?> {
        var jsonString = "{}"
        if (body != null) {
            val buffer = Buffer()
            body.writeTo(buffer)
            jsonString = buffer.readString(StandardCharsets.UTF_8)
        }
        try {
            val jsonObject = JSONObject(jsonString)
            return JsonMapUtil.jsonToMap(jsonObject)
        } catch (e: Exception) {
            return HashMap()
        }

    }


    private fun interceptRequestWhenDebug(
        chain: Interceptor.Chain,
        path: String,
        query: Map<String, String>,
        body: Map<String?, Any?>
    ): Response? {
        var response: Response? = null
        if (BuildConfig.DEBUG) {
            val request = chain.request()
            if (path.contains(CityListApi.URL_CITY_LIST)) {
                response = getMockJsonData(request, "city_list.mock")
            }
            else if (path.contains(SignApi.SIGN_IN)){
                response = getMockJsonData(request, "sign_in.mock")
            }

        }
        return response
    }


    private fun getHttpSuccessResponse(
        request: Request,
        dataJson: String?
    ): Response {
        return if (TextUtils.isEmpty(dataJson)) {
            Log.w(
                TAG,
                "getHttpSuccessResponse: dataJson is empty!"
            )
            Response.Builder()
                .code(500)
                .protocol(Protocol.HTTP_1_0)
                .request(request)
                .build()
        } else {
            Response.Builder()
                .code(200)
                .message(dataJson!!)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("Content-Type", "application/json")
                .body(dataJson.toResponseBody("application/json".toMediaType())).build()
        }
    }


    private fun getMockJsonData(
        request: Request,
        fileName: String
    ): Response? {
        val data: String?
        val assetManager = context.assets
        data = try {
            readStringFromInputStream(assetManager.open("$ASSET_DIR_MOCK_API/$fileName"))
        } catch (e: IOException) {
            Log.e(TAG, "getMockJsonData exception: $e")
            return null
        }
        val response: Response
        response = getHttpSuccessResponse(request, data)
        return response
    }

    companion object {
        private val TAG = MockApiInterceptor::class.java.simpleName

        @Throws(UnsupportedEncodingException::class)
        fun splitQuery(url: URL): Map<String, String> {
            val query_pairs: MutableMap<String, String> =
                LinkedHashMap()
            val query = url.query
            if (query != null) {
                val pairs = query.split("&").toTypedArray()
                for (pair in pairs) {
                    val idx = pair.indexOf('=')
                    query_pairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
                }
            }
            return query_pairs
        }
    }

}