package com.rohit.feildseye.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rohit.feildseye.R
import com.rohit.feildseye.databinding.FragmentMoistureBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.CoroutineContext

data class MoistureData(val soilMoisture: Int?)

class MoistureFragment : Fragment(), CoroutineScope {

    private var _binding: FragmentMoistureBinding? = null
    private val binding get() = _binding!!

    private val client = OkHttpClient()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoistureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchMoistureData()
    }

    private fun fetchMoistureData() {
        if (!isInternetAvailable()) {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            Log.e("MoistureFragment", "No network available")
            return
        }

        launch {
            val request = Request.Builder()
                .url("http://192.168.115.254/")
                .build()

            withContext(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()
                    val responseData = response.body?.string()

                    if (!responseData.isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            parseAndDisplayData(responseData)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Empty response from server", Toast.LENGTH_SHORT).show()
                        }
                        Log.e("MoistureFragment", "Empty server response")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("MoistureFragment", "Network error: ${e.message}", e)
                }
            }
        }
    }

    private fun parseAndDisplayData(responseData: String) {
        try {
            val moistureData = Gson().fromJson(responseData, MoistureData::class.java)

            val moistureValue = moistureData.soilMoisture ?: "N/A"
            binding.moistureLevelTextView.text = getString(R.string.soil_moisture_format, moistureValue)

        } catch (e: JsonSyntaxException) {
            Toast.makeText(requireContext(), "Error parsing data", Toast.LENGTH_SHORT).show()
            Log.e("MoistureFragment", "JSON parsing error: ${e.message}", e)
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}