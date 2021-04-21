package com.sample.mvp_rxjava_architecture.util

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.widget.CustomKeyBoardView

class KeyBoardUtil(private val keyboardView: CustomKeyBoardView, private val parent: View) :
    OnKeyboardActionListener {


    init {
        this.keyboardView.setOnKeyboardActionListener(this)
        this.keyboardView.keyboard = Keyboard(keyboardView.context, R.xml.keyboard)
        this.keyboardView.isEnabled = true
        this.keyboardView.isPreviewEnabled = false
    }


    private lateinit var mEditText: EditText


    fun showKeyboard(editText: EditText) {
        this.mEditText = editText

        //InputType.TYPE_NULL 禁止彈出系統鍵盤
        mEditText.inputType = InputType.TYPE_NULL
        parent.visibility = View.VISIBLE
    }


    fun hideKeyboard() {
        parent.visibility = View.INVISIBLE
    }


    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}
    override fun onText(text: CharSequence?) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}


    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val editable = mEditText.text
        val start = mEditText.selectionStart
        when (primaryCode) {
            -999 -> if (editable != null && editable.isNotEmpty()) {
                if (start > 0) {
                    editable.delete(start - 1, start)
                }
            }

            -100 -> plus(100)

            -1000 -> plus(1000)

            -10000 -> plus(10000)

            48 -> {
                if(editable.isNotEmpty()){
                    editable.insert(start, primaryCode.toChar().toString())
                }
            }
            -48 -> {
               if(editable.isNotEmpty()){
                   editable.insert(start, "00")
               }
            }

            else -> {
                editable.insert(start, primaryCode.toChar().toString())
            }
        }
    }


    private fun plus(count: Long) {
        val input = if (mEditText.text.toString() == "") "0" else mEditText.text.toString()
        mEditText.setText((input.toLong() + count).toString())
        mEditText.setSelection(mEditText.text.length)
    }


}