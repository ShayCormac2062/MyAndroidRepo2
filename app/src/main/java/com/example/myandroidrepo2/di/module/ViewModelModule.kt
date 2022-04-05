package com.example.myandroidrepo2.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myandroidrepo2.di.ViewModelKey
import com.example.myandroidrepo2.presentation.viewmodel.WeatherViewModel
import com.example.myandroidrepo2.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun bindMainViewModel(
        viewModel: WeatherViewModel
    ): ViewModel
}
