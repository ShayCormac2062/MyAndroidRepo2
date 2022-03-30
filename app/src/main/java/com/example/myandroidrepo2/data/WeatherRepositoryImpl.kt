package com.example.myandroidrepo2.data

import com.example.myandroidrepo2.data.api.APIService
import com.example.myandroidrepo2.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val api: APIService) : WeatherRepository {

    override suspend fun getWeather(city: String) = api.getWeather(city)
    override suspend fun getWeatherWithLocation(lon: Double?, lat: Double?) = api.getWeatherWithCoordinates(lon, lat)
    override suspend fun getWeatherList(lon: Double?, lat: Double?, count: Int) = api.getWeatherList(lon, lat, count)

}
