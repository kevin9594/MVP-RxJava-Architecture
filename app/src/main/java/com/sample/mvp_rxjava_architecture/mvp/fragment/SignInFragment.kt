package com.sample.mvp_rxjava_architecture.mvp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.base.BasePresenterFragment
import com.sample.mvp_rxjava_architecture.bean.SignBean
import com.sample.mvp_rxjava_architecture.mvp.contract.SignContract
import com.sample.mvp_rxjava_architecture.mvp.presenter.SignPresenter
import com.sample.mvp_rxjava_architecture.util.KeyBoardUtil
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : BasePresenterFragment<SignContract.View, SignPresenter>(), SignContract.View,
    View.OnClickListener {

    companion object {

        var title = R.string.tab_title_sign_in

    }

    var count = 0

    private var keyboard: KeyBoardUtil? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_sign_in.setOnClickListener(this)
        edit_text.setOnClickListener(this)
        edit_text2.setOnClickListener(this)
        tv_close.setOnClickListener(this)

        keyboard = KeyBoardUtil(kv_keyboard, ll_keyboard)

    }


    override fun onCreatePresenter(): SignPresenter {
        return SignPresenter()
    }


    @SuppressLint("SetTextI18n")
    override fun success(signBean: SignBean) {
        tv_data.text =
            "姓名 : " + signBean.userName + "\n年齡 : " + signBean.age + "\nID : " + signBean.userId

    }


    override fun fail(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    override fun onClick(v: View?) {

        when (v) {
            bt_sign_in -> {

//                count++
//                edit_text.setText("$count")

//                mPresenter?.getData("Michael Jordan", "a123")
            }

            tv_close -> {
                keyboard?.hideKeyboard()
            }

            edit_text -> {
                keyboard?.showKeyboard(edit_text)
            }

            edit_text2 -> {
                keyboard?.showKeyboard(edit_text2)
            }

        }

    }
}