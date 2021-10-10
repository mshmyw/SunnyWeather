package com.sunnyweather.android.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.RealtimeResponse
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import com.sunnyweather.android.logic.model.getSkyByWeather

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
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        findViewById<Button>(R.id.navBtn).setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
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
//        currentSky.text = getSky(skycon).info
        currentSky.text = realtime.weather
        val nowLayout = findViewById<RelativeLayout>(R.id.nowLayout)
        val sky = getSkyByWeather(realtime.weather)
        nowLayout.setBackgroundResource(sky.bg)
        val currentHumidity = findViewById<TextView>(R.id.currentHumidity)
        currentHumidity.text = "湿度：${realtime.humidity}"

        // TODO 未来几天的天气数据

        val weatherLayout = findViewById<ScrollView>(R.id.weatherLayout)
        weatherLayout.visibility = View.VISIBLE
    }
}