package com.rohit.feildseye

data class WeatherData(
    val name: String,             // City name
    val main: Main,               // Main weather data (temperature, humidity)
    val weather: List<Weather>,   // List of weather conditions (for icon)
    val wind: Wind                // Wind data (wind speed)
)

data class Main(
    val temp: Double,             // Temperature
    val humidity: Int             // Humidity (in percentage)
)

data class Weather(
    val icon: String              // Weather icon identifier
)

data class Wind(
    val speed: Double             // Wind speed (in meters per second)
)
