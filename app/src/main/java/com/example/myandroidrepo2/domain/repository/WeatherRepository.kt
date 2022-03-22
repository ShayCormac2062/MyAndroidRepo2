package com.example.myandroidrepo2.domain.repository

import com.example.myandroidrepo2.domain.WeatherDetail
import com.example.myandroidrepo2.domain.WeatherListModel

interface WeatherRepository {

    suspend fun getWeather(city: String): WeatherDetail
    suspend fun getWeatherWithLocation(lon: Double?, lat: Double?): WeatherDetail
    suspend fun getWeatherList(lon: Double?, lat: Double?, count: Int): WeatherListModel
}
