package com.example.myandroidrepo2.domain.usecase

import com.example.myandroidrepo2.data.WeatherRepositoryImpl
import com.example.myandroidrepo2.domain.WeatherDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherUseCase(
    private val repository: WeatherRepositoryImpl,
    private val scope: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(city: String): WeatherDetail =
        withContext(scope) {
            repository.getWeather(city)
        }
}
