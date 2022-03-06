package com.example.myandroidrepo2.data.api

import com.example.myandroidrepo2.domain.WeatherDetail
import com.example.myandroidrepo2.data.api.entity.WeatherListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("weather?units=metric")
    suspend fun getWeather(@Query("q") city: String): WeatherDetail

    @GET("find?units=metric&lang=ru")
    suspend fun getWeatherWithCoordinates(@Query("lon") longitude: Double?,
                                          @Query("lat") latitude: Double?
    ): WeatherDetail

    @GET("find?units=metric&lang=ru")
    suspend fun getWeatherList(
        @Query("lat") latitude:Double?,
        @Query("lon") longitude:Double?,
        @Query("cnt") count:Int
    ): WeatherListModel
}
