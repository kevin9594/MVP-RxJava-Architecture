package com.sample.mvp_rxjava_architecture.util

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.sample.mvp_rxjava_architecture.R

class KeyBoardUtil(keyboardView: KeyboardView, editText: EditText) {
    private val keyboardView: KeyboardView
    private val editText: EditText
    private val keyboard // 自定义键盘
            : Keyboard? = null
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
                Keyboard.KEYCODE_DELETE -> if (editable != null && editable.length > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start)
                    }
                }
                Keyboard.KEYCODE_CANCEL -> keyboardView.visibility = View.GONE
                else -> editable!!.insert(start, Character.toString(primaryCode.toChar()))
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
            keyboardView.visibility = View.GONE
        }
    }

    init {
        //setInputType为InputType.TYPE_NULL   不然会弹出系统键盘
        editText.inputType = InputType.TYPE_NULL
        this.keyboardView = keyboardView
        this.editText = editText
        this.keyboardView.setOnKeyboardActionListener(listener)
        this.keyboardView.keyboard = Keyboard(editText.context, R.xml.keyboard)
        this.keyboardView.isEnabled = true
        this.keyboardView.isPreviewEnabled = false
    }
}