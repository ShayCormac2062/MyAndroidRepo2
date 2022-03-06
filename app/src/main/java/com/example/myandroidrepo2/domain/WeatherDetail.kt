package com.example.myandroidrepo2.domain

import com.example.myandroidrepo2.data.api.entity.Clouds
import com.example.myandroidrepo2.data.api.entity.Coord
import com.example.myandroidrepo2.data.api.entity.Main
import com.example.myandroidrepo2.data.api.entity.Sys
import com.example.myandroidrepo2.data.api.entity.Weather
import com.example.myandroidrepo2.data.api.entity.Wind

data class WeatherDetail(
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val rain: Any,
    val snow: Any,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
