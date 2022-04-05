package com.example.myandroidrepo2.di.module

import android.content.Context
import com.example.myandroidrepo2.App
import com.example.myandroidrepo2.data.WeatherRepositoryImpl
import com.example.myandroidrepo2.data.api.APIService
import com.example.myandroidrepo2.domain.repository.WeatherRepository
import com.example.myandroidrepo2.domain.usecase.GetWeatherListUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherWithLocationUseCase
import com.example.myandroidrepo2.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun provideWeatherRepository(
        api: APIService
    ): WeatherRepository = WeatherRepositoryImpl(api)

    @Provides
    fun provideGetWeatherUseCase(
        repository: WeatherRepository,
        provider: DispatcherProvider
    ): GetWeatherUseCase = GetWeatherUseCase(repository, provider)

    @Provides
    fun provideGetWeatherListUseCase(
        repository: WeatherRepository,
        provider: DispatcherProvider
    ): GetWeatherListUseCase = GetWeatherListUseCase(repository, provider)

    @Provides
    fun provideGetWeatherWithLocationUseCase(
        repository: WeatherRepository,
        provider: DispatcherProvider
    ): GetWeatherWithLocationUseCase = GetWeatherWithLocationUseCase(repository, provider)

}
