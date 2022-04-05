package com.example.myandroidrepo2.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myandroidrepo2.App
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.databinding.ActivityMainBinding
import com.example.myandroidrepo2.domain.usecase.GetWeatherListUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherUseCase
import com.example.myandroidrepo2.domain.usecase.GetWeatherWithLocationUseCase
import com.example.myandroidrepo2.presentation.ui.fragment.MainFragment
import com.example.myandroidrepo2.presentation.viewmodel.WeatherViewModel
import com.example.myandroidrepo2.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment(null))
            .addToBackStack(null)
            .commit()
    }
}
