package com.sunnyweather.android.ui.place

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.sunnyweather.android.R
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class
PlaceFragment : Fragment() {

    val viewModel by lazy {
        ViewModelProviders.of(this).get(PlaceViewModel::class.java)
    }
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recycleView)
        if (recyclerView != null) {
            recyclerView.layoutManager = layoutManager
        }

        adapter = PlaceAdapter(this, viewModel.placeList)
        if (recyclerView != null) {
            recyclerView.adapter= adapter
        }

        activity?.findViewById<EditText>(R.id.searchPlaceEdit)?.addTextChangedListener{
            editable ->
            val content = editable.toString()
            if(content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                if (recyclerView != null) {
                    recyclerView.visibility = View.GONE
                }
                requireActivity().findViewById<ImageView>(R.id.bgImageView).visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer {
            result ->
            val places = result.getOrNull()
            if(places != null) {
                Log.d("viewModel", "$places");

                if (recyclerView != null) {
                    recyclerView.visibility = View.VISIBLE
                }
                requireActivity().findViewById<ImageView>(R.id.bgImageView).visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}


