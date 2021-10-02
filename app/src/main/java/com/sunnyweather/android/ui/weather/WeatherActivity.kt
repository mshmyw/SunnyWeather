package com.sunnyweather.android.ui.weather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.RealtimeResponse
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if(viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        viewModel.weatherLiveData.observe(this, Observer {
            result ->
            val weather = result.getOrNull()
            if(weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT)
                    .show()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setColorSchemeColors(R.color.design_default_color_on_primary)
        refreshWeather()
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        viewModel.refreshWeather(viewModel.placeName)
    }
    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.placeName)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.isRefreshing = true
    }
    private fun showWeatherInfo(weather: Weather) {
        val placeName = findViewById<TextView>(R.id.placeName)
        placeName.text = viewModel.placeName
        val realtime = weather.realtime.lives[0]
        val currentTempText = "${realtime.temperature.toInt()}"
        val currentTemp = findViewById<TextView>(R.id.currentTemp)
        currentTemp.text = currentTempText
        val currentSky = findViewById<TextView>(R.id.currentSky)
        // TODO
        val skycon = "CLEAR_DAY"
        currentSky.text = getSky(skycon).info
        val nowLayout = findViewById<RelativeLayout>(R.id.nowLayout)
        nowLayout.setBackgroundResource(getSky(skycon).bg)

        // TODO 未来几天的天气数据

        val weatherLayout = findViewById<ScrollView>(R.id.weatherLayout)
        weatherLayout.visibility = View.VISIBLE
    }
}