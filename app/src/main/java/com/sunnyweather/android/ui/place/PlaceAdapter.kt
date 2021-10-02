package com.sunnyweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Live
import com.sunnyweather.android.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Live>)
    : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val placeName: TextView = view.findViewById(R.id.placeName)
            val placeAddress: TextView = view.findViewById(R.id.placeAddress)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if(activity is WeatherActivity) {
                activity.findViewById<DrawerLayout>(R.id.drawerLayout)
                    .closeDrawers()
                activity.viewModel.placeName = place.city
                activity.refreshWeather()

            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("place_name", place.city)
                }

                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.viewModel.savePlace(place)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.city
        holder.placeAddress.text = place.adcode
    }

    override fun getItemCount() = placeList.size
}
