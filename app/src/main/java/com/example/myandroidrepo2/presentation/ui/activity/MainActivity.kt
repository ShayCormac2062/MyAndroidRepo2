package com.example.myandroidrepo2.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.data.WeatherRepositoryImpl
import com.example.myandroidrepo2.data.di.DIContainer
import com.example.myandroidrepo2.databinding.ActivityMainBinding
import com.example.myandroidrepo2.domain.usecase.GetWeatherListUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherWithLocationUseCase
import com.example.myandroidrepo2.presentation.ui.fragment.MainFragment
import com.example.myandroidrepo2.presentation.viewmodel.WeatherViewModel
import com.example.myandroidrepo2.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObjects()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment(null))
            .addToBackStack(null)
            .commit()
    }

    private fun initObjects() {
        val repository = WeatherRepositoryImpl(DIContainer.api)
        val factory = ViewModelFactory(
            GetWeatherUseCase(repository),
            GetWeatherWithLocationUseCase(repository),
            GetWeatherListUseCase(repository)
        )
        viewModel = ViewModelProvider(
            this,
            factory
        )[WeatherViewModel::class.java]
    }
}
