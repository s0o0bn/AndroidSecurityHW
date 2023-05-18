package com.yoonsoobin.insecuremoviereview.util

import android.content.Context
import android.widget.Toast

class ToastUtil {
    companion object {
        fun makeToast(context: Context, text: String): Toast =
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
    }
}