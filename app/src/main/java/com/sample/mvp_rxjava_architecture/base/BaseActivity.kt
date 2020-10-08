package com.sample.mvp_rxjava_architecture.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseActivity : AppCompatActivity(), BaseContract.View, View.OnClickListener {

    fun openActivity(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
    }


    fun openActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(intent, requestCode, bundle)
    }


    fun openActivityWithClose(cls: Class<*>?, bundle: Bundle?) {
        openActivity(cls, bundle)
        finish()
    }


    fun setToolBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { finish() }
    }


    fun setToolBar(title: Int) {
        tv_toolbar_title.setText(title)
        iv_toolbar_back.setOnClickListener { finish() }
    }


    fun setToolBar(title: String) {
        tv_toolbar_title.text = title
        iv_toolbar_back.setOnClickListener { finish() }
    }


    fun hideToolBar() {
        toolbar.visibility = View.GONE
    }


    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun showToast(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }


    override fun showProgressDialog() {

    }


    override fun dismissProgressDialog() {

    }


    override fun requestSignInAndGoToSignIn(message: String, isFinish: Boolean) {

    }


    override fun onClick(v: View?) {
        when (v) {
            iv_toolbar_right_button -> {

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }

}