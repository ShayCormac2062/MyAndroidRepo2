package com.example.myandroidrepo2.domain.usecase

import com.example.myandroidrepo2.data.WeatherRepositoryImpl
import com.example.myandroidrepo2.data.api.entity.WeatherListModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherListUseCase(private val repository: WeatherRepositoryImpl,
                            private val scope: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(longitude: Double, latitude: Double): WeatherListModel =
        withContext(scope) {
            repository.getWeatherList(longitude, latitude, 20)
        }

}
