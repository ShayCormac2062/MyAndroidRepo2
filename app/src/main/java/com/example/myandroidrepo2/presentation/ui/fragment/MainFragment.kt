package com.example.myandroidrepo2.presentation.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.databinding.FragmentMainBinding
import com.example.myandroidrepo2.domain.WeatherDetail
import com.example.myandroidrepo2.presentation.ui.activity.MainActivity
import com.example.myandroidrepo2.presentation.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.*

class MainFragment(private var city: String?) : Fragment() {

    private var binding: FragmentMainBinding? = null
    private lateinit var locationClient: FusedLocationProviderClient
    private var longitude: Double? = null
    private var latitude: Double? = null
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weather: WeatherDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        viewModel = (requireActivity() as MainActivity).viewModel
        startLoading()
        initObservers()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.beginTransaction()
            .add(R.id.container, LoadingFragment())
            .addToBackStack(null)
            .commit()
        setupLocation()
        binding?.btnSearchCity?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SearchCityFragment(longitude, latitude)
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLocation()
                } else {
                    Toast.makeText(context, getString(R.string.message_location_deny), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun startLoading() {
        lifecycleScope.launch {
            if (city != null) {
                viewModel.getWeatherForCity(city.toString())
            } else {
                if (longitude == null) {
                    city = "Kazan"
                    viewModel.getWeatherForCity(city.toString())
                } else {
                    viewModel.getWeatherWithLocation(longitude, latitude)
                }
            }
        }
    }
    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { wd ->
                binding?.weather = wd
                setupWeatherDetails(wd)
            }, onFailure = {
                Log.e("asd", it.message.toString())
            })
        }
    }

    private fun setMonth(i: Int): String =
        when (i) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            else -> "Dec"
        }

    private fun setupWeatherDetails(data: WeatherDetail?) {
        val calendar = Calendar.getInstance()
        data?.let { detail ->
            binding?.run {
                tvCalendar.text = "${calendar[Calendar.DAY_OF_MONTH]}, " +
                    "${setMonth(calendar[Calendar.MONTH])} ${calendar[Calendar.YEAR]}"
                calendar.timeInMillis = detail.sys.sunrise.toLong()
                tvSunriseResult.text = setHour(calendar, 0)
                calendar.timeInMillis = detail.sys.sunset.toLong()
                tvSunsetResult.text = setHour(calendar, 1)
                tvDirectionResult.text = setDirection(detail.wind.deg)
                val uri: Uri =
                    Uri.parse("https://openweathermap.org/img/wn/${detail.weather[0].icon}@2x.png")
                ivDescription.load(uri)
            }
            parentFragmentManager.popBackStack()
        }
    }

    private fun setDirection(deg: Int): String =
        when (deg) {
            in (0..22) -> "N"
            in (23..67) -> "NE"
            in (68..112) -> "E"
            in (113..157) -> "SE"
            in (158..202) -> "S"
            in (203..247) -> "SW"
            in (248..292) -> "S"
            in (293..337) -> "NW"
            else -> "N"
        }

    private fun setHour(calendar: Calendar, check: Int): String {
        val hour: Int =
            if (check == 1) calendar[Calendar.HOUR_OF_DAY] + 12 else calendar[Calendar.HOUR_OF_DAY]
        val hours = if (hour < 10)
            "0$hour" else "$hour"
        val minutes = if (calendar[Calendar.MINUTE] < 10)
            "0${calendar[Calendar.MINUTE]}" else "${calendar[Calendar.MINUTE]}"
        return "$hours:$minutes"
    }

    private fun setupLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            requestPermissions(permissions, 100)
        } else {
            locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                latitude = location?.latitude
                longitude = location?.longitude
                if (location == null) {
                    Log.e("FUCK", "Не получилось найти локацию")
                }
            }
        }
    }
}
