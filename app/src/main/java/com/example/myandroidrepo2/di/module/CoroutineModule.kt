package com.example.myandroidrepo2.di.module

import com.example.myandroidrepo2.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    fun provideScope(): DispatcherProvider = DispatcherProvider()
}
