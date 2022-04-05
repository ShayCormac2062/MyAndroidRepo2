package com.example.myandroidrepo2.domain.usecase

import com.example.myandroidrepo2.data.WeatherRepositoryImpl
import com.example.myandroidrepo2.domain.WeatherListModel
import com.example.myandroidrepo2.domain.repository.WeatherRepository
import com.example.myandroidrepo2.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(private val repository: WeatherRepository,
                                                private val scope: DispatcherProvider
) {

    suspend operator fun invoke(longitude: Double?, latitude: Double?): WeatherListModel =
        withContext(scope.IO) {
            repository.getWeatherList(longitude, latitude, 30)
        }

}
