package com.example.myandroidrepo2.di

import com.example.myandroidrepo2.App
import com.example.myandroidrepo2.di.module.AppModule
import com.example.myandroidrepo2.di.module.CoroutineModule
import com.example.myandroidrepo2.di.module.NetModule
import com.example.myandroidrepo2.di.module.ViewModelModule
import com.example.myandroidrepo2.presentation.ui.activity.MainActivity
import com.example.myandroidrepo2.presentation.ui.fragment.MainFragment
import com.example.myandroidrepo2.presentation.ui.fragment.SearchCityFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(
    modules = [
        AppModule::class,
        NetModule::class,
        ViewModelModule::class,
        CoroutineModule::class,
    ]
)]
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: SearchCityFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent

    }
}
