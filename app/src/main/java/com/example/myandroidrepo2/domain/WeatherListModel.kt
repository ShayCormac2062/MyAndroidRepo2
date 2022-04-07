package com.example.myandroidrepo2.domain

data class WeatherListModel(
    val cod: String,
    val count: Int,
    val list: List<WeatherDetail>,
    val message: String
)
