package com.example.myandroidrepo2.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.databinding.FragmentSearchCityBinding
import com.example.myandroidrepo2.presentation.adapter.WeatherListAdapter
import com.example.myandroidrepo2.presentation.ui.activity.MainActivity
import com.example.myandroidrepo2.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

class SearchCityFragment(
    private var longitude: Double?,
    private var latitude: Double?,
) : Fragment() {

    private var binding: FragmentSearchCityBinding? = null
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(layoutInflater)
        viewModel = (requireActivity() as MainActivity).viewModel
        initObservers()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            initSearch()
            lifecycleScope.launch {
                viewModel.getWeatherList(
                    longitude ?: 55.7877,
                    latitude ?: 49.1221
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { wd ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment(wd.name))
                    .addToBackStack(null)
                    .commit()
            }, onFailure = {
                Log.e("asd", it.message.toString())
            })
        }
        viewModel.weatherList.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { wd ->
                binding?.rvWeatherList?.apply {
                    adapter = WeatherListAdapter(wd).apply {
                        onClick = {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.container, MainFragment(it))
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                    layoutManager = GridLayoutManager(this@SearchCityFragment.context, 2)
                }
            }, onFailure = {})
        }
    }

    private fun initSearch() {
        binding?.searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    try {
                        viewModel.getWeatherForCity(query)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            context,
                            "Couldn't to find the city.Try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

        })
    }
}
