package com.rohit.feildseye.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rohit.feildseye.R
import com.rohit.feildseye.WeatherData
import com.rohit.feildseye.WeatherService
import com.rohit.feildseye.databinding.FragmentAgriguideBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AgriguideFragment : Fragment() {

    private var _binding: FragmentAgriguideBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherService: WeatherService

    private val apiKey = "d0bce3b50d931baf6b3867064f44b420"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgriguideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up Retrofit for weather API
        weatherService = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)


        fetchWeatherData("")

        // Fetch weather on button click with user-entered city
        binding.fetchWeatherButton.setOnClickListener {
            val city = binding.cityInput.text.toString().trim()
            if (city.isNotEmpty()) {
                fetchWeatherData(city)
            }
        }
    }

    private fun fetchWeatherData(cityName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val weatherData = weatherService.getWeather(cityName, apiKey)
                withContext(Dispatchers.Main) {
                    updateUI(weatherData)
                }
            } catch (e: Exception) {
                // Handle the error (e.g., show a message)
                withContext(Dispatchers.Main) {
                    binding.weatherCityTitle.text = "Error fetching weather data"
                    binding.weathTempTextWeathFrag.text = "-"
                    binding.windTextWeathFrag.text = "Wind: - km/h"
                    binding.humidityTextWeathFrag.text = "Humidity: - %"
                }
            }
        }
    }

    private fun updateUI(weatherData: WeatherData) {
        // Update city name
        binding.weatherCityTitle.text = weatherData.name

        // Convert temperature from Kelvin to Celsius
        val tempInCelsius = weatherData.main.temp - 273.15
        binding.weathTempTextWeathFrag.text = String.format("%.2f °C", tempInCelsius)

        // Update other UI elements as necessary
        binding.windTextWeathFrag.text = "Wind: ${weatherData.wind.speed} km/h"
        binding.humidityTextWeathFrag.text = "Humidity: ${weatherData.main.humidity} %"

        // Load weather icon
        val iconUrl = "https://openweathermap.org/img/wn/${weatherData.weather[0].icon}@2x.png"
        Glide.with(requireContext()).load(iconUrl).into(binding.weathIconImageWeathFrag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
