package com.sample.mvp_rxjava_architecture

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide
import com.sample.mvp_rxjava_architecture.manager.NetworkStatusManager
import com.sample.mvp_rxjava_architecture.manager.RequestManager


class SampleApp : MultiDexApplication() {
    companion object {
        val TAG = SampleApp::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        RequestManager.init(this)
        NetworkStatusManager.init(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        try {
            Glide.get(this).clearMemory()
        } catch (t: Throwable) {
            Log.e(SampleApp::class.java.simpleName, t.toString())
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        try {
            Glide.get(this).trimMemory(level)
        } catch (t: Throwable) {
            Log.e(SampleApp::class.java.simpleName, t.toString())
        }
    }
}
