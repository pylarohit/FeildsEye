package com.rohit.feildseye.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var ecommerceAdapter: EcommerceAdapter
    private val itemsList = arrayListOf(
        "Agriculture Tractor",
        "Agriculture Fertilizer",
        "Mild Steel Tractor Cultivator",
        "Agriculture Drone",
        "Agriculture Seeder"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgriguideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        ecommerceAdapter = EcommerceAdapter(itemsList)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.ecommrcyclr.layoutManager = linearLayoutManager
        binding.ecommrcyclr.adapter = ecommerceAdapter

        binding.cat1.setOnClickListener {
            findNavController().navigate(R.id.action_agriguideFragment_to_RecommendFragment)
        }
        binding.cat2.setOnClickListener {
            findNavController().navigate(R.id.action_agriguideFragment_to_SoilFertilizerFragment)
        }
        binding.cat3.setOnClickListener {
            findNavController().navigate(R.id.action_agriguideFragment_to_SoilQualityFragment)
        }

        // Set up RecyclerView item click listener
        ecommerceAdapter.setOnItemClickListener(object : EcommerceAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProductFragment)
                    1 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct1Fragment)
                    2 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct2Fragment)
                    3 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct3Fragment)
                    4 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct4Fragment)
                }
            }
        })

        // Set up Retrofit for weather API
        weatherService = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)


        fetchWeatherData("delhi")

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
