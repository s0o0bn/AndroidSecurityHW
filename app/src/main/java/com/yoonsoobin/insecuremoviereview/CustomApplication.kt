package com.yoonsoobin.insecuremoviereview

import android.app.Application
import com.yoonsoobin.insecuremoviereview.data.AppDatabase

class CustomApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}