package com.example.myandroidrepo2.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.databinding.ActivityMainBinding
import com.example.myandroidrepo2.presentation.ui.fragment.MainFragment

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
