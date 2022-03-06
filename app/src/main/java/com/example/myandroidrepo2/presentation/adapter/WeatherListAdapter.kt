package com.example.myandroidrepo2.presentation.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidrepo2.R
import com.example.myandroidrepo2.databinding.OneCityViewBinding
import com.example.myandroidrepo2.domain.WeatherDetail
import com.example.myandroidrepo2.data.api.entity.WeatherListModel

class WeatherListAdapter(
    allWeather: WeatherListModel
) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    var onClick: ((String) -> (Unit))? = null
    private val weatherList: List<WeatherDetail> = allWeather.list

    inner class WeatherListViewHolder(
        private val binding: OneCityViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherDetail) = with(binding) {
            cardCity.setCardBackgroundColor(R.drawable.gradient_night)
            rvItemWeatherCardTown.text = item.name
            with(cardCity) {
                setOnClickListener {
                    it.alpha = 0.5F
                    onClick?.invoke(item.name)
                }
            }
            rvItemWeatherCardTemp.text = "${item.main.temp.toInt()}°C"
            setupGradusnik(item.main.temp.toInt(), rvItemWeatherCardThermo, cardCity)
            setupAir(item.wind.speed.toInt(), binding.rvItemWeatherCardAir, cardCity)
            setupRain(
                if (item.main.humidity > 20) true else null,
                if (item.clouds.all > 0) true else null,
                binding.rvItemWeatherCardUmbrella,
                cardCity
            )
        }

        private fun setupGradusnik(
            temperature: Int,
            weatherThermometer: ImageView,
            itemView: View
        ) {
            when (temperature) {
                in 20..100 -> weatherThermometer.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.orange),
                    PorterDuff.Mode.MULTIPLY
                )
                in 10..20 -> weatherThermometer.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.yellow_light),
                    PorterDuff.Mode.MULTIPLY
                )
                in 0..10 -> weatherThermometer.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.green),
                    PorterDuff.Mode.MULTIPLY
                )
                in -10..0 -> weatherThermometer.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.teal_700),
                    PorterDuff.Mode.MULTIPLY
                )
                in -20..-10 -> weatherThermometer.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.purple_700),
                    PorterDuff.Mode.MULTIPLY
                )
                in -100..-10 -> weatherThermometer.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.blue_dark),
                    PorterDuff.Mode.MULTIPLY
                )
            }
        }

        private fun setupAir(airSpeed: Int, weatherAir: ImageView, itemView: View) {
            when (airSpeed) {
                in 15..100 -> weatherAir.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.orange),
                    PorterDuff.Mode.MULTIPLY
                )
                in 7..15 -> weatherAir.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.yellow),
                    PorterDuff.Mode.MULTIPLY
                )
                in 0..15 -> weatherAir.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.green),
                    PorterDuff.Mode.MULTIPLY
                )
            }
        }

        fun setupRain(rain: Any?, snow: Any?, weatherUmbrella: ImageView, itemView: View) {
            if (rain != null || snow != null)
                weatherUmbrella.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.teal_700),
                    PorterDuff.Mode.MULTIPLY
                )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder =
        WeatherListViewHolder(
            OneCityViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) =
        holder.bind(weatherList[position])

    override fun getItemCount(): Int = weatherList.size

}
