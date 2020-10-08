package com.sample.mvp_rxjava_architecture.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseFragment : Fragment(), BaseContract.View, View.OnClickListener {

    fun openActivity(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(activity, cls)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
    }


    fun openActivityWithClose(cls: Class<*>?, bundle: Bundle?) {
        openActivity(cls, bundle)
        activity?.finish()
    }


    fun openActivityForResult(cls: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(activity, cls)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(intent, requestCode, bundle)
    }


    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    override fun showToast(id: Int) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
    }


    fun setToolBar(title: Int) {
        tv_toolbar_title.setText(title)
        iv_toolbar_back.setOnClickListener { activity?.finish() }
    }


    fun setToolBar(title: String) {
        tv_toolbar_title.text = title
        iv_toolbar_back.setOnClickListener { activity?.finish() }
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