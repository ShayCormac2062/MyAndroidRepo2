package com.example.myandroidrepo2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroidrepo2.domain.WeatherListModel
import com.example.myandroidrepo2.domain.WeatherDetail
import com.example.myandroidrepo2.domain.usecase.GetWeatherListUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherWithLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase,
                       private val getWeatherWithLocationUseCase: GetWeatherWithLocationUseCase,
                       private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModel() {
    private var _weather: MutableLiveData<Result<WeatherDetail>?> = MutableLiveData()
    val weather: LiveData<Result<WeatherDetail>?> = _weather

    private var _weatherList: MutableLiveData<Result<WeatherListModel>?> = MutableLiveData()
    val weatherList: LiveData<Result<WeatherListModel>?> = _weatherList

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    suspend fun getWeatherForCity(city: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherUseCase(city)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    suspend fun getWeatherWithLocation(longitude: Double?, latitude: Double?) {
        viewModelScope.launch {
            try {
                val weather = getWeatherWithLocationUseCase(longitude, latitude)
                _weather.value = Result.success(weather)
                _weather.postValue(null)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    suspend fun getWeatherList(longitude: Double?, latitude: Double?) {
        viewModelScope.launch {
            try {
                val weatherList = getWeatherListUseCase(longitude, latitude)
                _weatherList.value = Result.success(weatherList)
                _weatherList.postValue(null)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
