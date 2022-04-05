package com.example.myandroidrepo2

import android.app.Application
import com.example.myandroidrepo2.di.AppComponent
import com.example.myandroidrepo2.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
