package com.example.myandroidrepo2.entity

data class WeatherListModel(
    val cod: String,
    val count: Int,
    val list: List<WeatherDetail>,
    val message: String
)
