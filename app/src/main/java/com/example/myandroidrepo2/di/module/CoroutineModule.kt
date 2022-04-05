package com.example.myandroidrepo2.di.module

import com.example.myandroidrepo2.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class CoroutineModule {

    @Provides
    fun provideScope(): DispatcherProvider = DispatcherProvider()
}
