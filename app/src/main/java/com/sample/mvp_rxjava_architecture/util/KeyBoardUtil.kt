package com.sample.mvp_rxjava_architecture.util

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.widget.CustomKeyBoardView

class KeyBoardUtil(private val keyboardView: CustomKeyBoardView, private val editText: EditText) {

    private val keyboard: Keyboard? = Keyboard(editText.context, R.xml.keyboard)
    private val listener: OnKeyboardActionListener = object : OnKeyboardActionListener {
        override fun swipeUp() {}
        override fun swipeRight() {}
        override fun swipeLeft() {}
        override fun swipeDown() {}
        override fun onText(text: CharSequence) {}
        override fun onRelease(primaryCode: Int) {}
        override fun onPress(primaryCode: Int) {}
        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
            val editable = editText.text
            val start = editText.selectionStart
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> if (editable != null && editable.isNotEmpty()) {
                    if (start > 0) {
                        editable.delete(start - 1, start)
                    }
                }
                Keyboard.KEYCODE_CANCEL -> keyboardView.visibility = View.GONE
                else -> editable!!.insert(start, primaryCode.toChar().toString())
            }
        }
    }

    // Activity中获取焦点时调用，显示出键盘
    fun showKeyboard() {
        val visibility = keyboardView.visibility
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.visibility = View.VISIBLE
        }
    }

    // 隐藏键盘
    fun hideKeyboard() {
        val visibility = keyboardView.visibility
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            keyboardView.visibility = View.INVISIBLE
        }
    }

    init {
        //setInputType为InputType.TYPE_NULL   不然会弹出系统键盘
        editText.inputType = InputType.TYPE_NULL
        this.keyboardView.setOnKeyboardActionListener(listener)
        this.keyboardView.keyboard = keyboard
        this.keyboardView.isEnabled = true
        this.keyboardView.isPreviewEnabled = false
    }


}