package com.example.myandroidrepo2.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.presentation.adapter.WeatherListAdapter
import com.example.myandroidrepo2.databinding.FragmentSearchCityBinding
import com.example.myandroidrepo2.data.WeatherRepositoryImpl
import com.example.myandroidrepo2.domain.usecase.GetWeatherListUseCase
import kotlinx.coroutines.launch

class SearchCityFragment(private var longitude: Double?,
                         private var latitude: Double?,
                         private var repository: WeatherRepositoryImpl
                         ) : Fragment() {

    private var binding: FragmentSearchCityBinding? = null
    private lateinit var getWeatherListUseCase: GetWeatherListUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(layoutInflater)
        getWeatherListUseCase = GetWeatherListUseCase(repository)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            initSearch()
            rvWeatherList.apply {
                lifecycleScope.launch {
                    adapter = WeatherListAdapter(
                        getWeatherListUseCase(
                            longitude ?: 55.7877,
                            latitude ?: 49.1221)
                    ).apply {
                        onClick = {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.container, MainFragment(it))
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                }
                layoutManager = GridLayoutManager(this@SearchCityFragment.context, 2)
            }
        }
    }

    private fun initSearch(){
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    try {
                        val weather = repository.getWeather(query)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment(query))
                            .addToBackStack(null)
                            .commit()
                    }
                    catch (ex:Exception){
                        Toast.makeText(context,"Couldn't to find the city.Try again!",Toast.LENGTH_SHORT).show()
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
