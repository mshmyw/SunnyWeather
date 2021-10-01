package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SunnyWeatherApplication : Application() {

    companion object {
        const val TOKEN = "4a835315daf23bf05683af957d7045a0"  // gaode token
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}