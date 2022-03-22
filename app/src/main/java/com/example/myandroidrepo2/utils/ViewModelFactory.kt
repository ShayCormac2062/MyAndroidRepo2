package com.example.myandroidrepo2.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myandroidrepo2.domain.usecase.GetWeatherListUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherWithLocationUseCase
import com.example.myandroidrepo2.presentation.viewmodel.WeatherViewModel

class ViewModelFactory(private val getWeatherUseCase: GetWeatherUseCase,
                       private val getWeatherWithLocationUseCase: GetWeatherWithLocationUseCase,
                       private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(WeatherViewModel::class.java) ->
                WeatherViewModel(
                    getWeatherUseCase,
                    getWeatherWithLocationUseCase,
                    getWeatherListUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}
