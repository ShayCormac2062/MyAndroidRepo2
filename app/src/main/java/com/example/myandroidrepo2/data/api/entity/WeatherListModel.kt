package com.example.myandroidrepo2.data.api.entity

import com.example.myandroidrepo2.domain.WeatherDetail

data class WeatherListModel(
    val cod: String,
    val count: Int,
    val list: List<WeatherDetail>,
    val message: String
)
