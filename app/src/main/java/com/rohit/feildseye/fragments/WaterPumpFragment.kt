package com.rohit.feildseye.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rohit.feildseye.databinding.FragmentWaterPumpBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

class WaterPumpFragment : Fragment() {

    private lateinit var binding: FragmentWaterPumpBinding

    companion object {
        private const val ESP32_BASE_URL = "http://192.168.115.254"

        @JvmStatic
        fun newInstance() = WaterPumpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaterPumpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up button listeners
        binding.btnTurnOn.setOnClickListener {
            sendCommandToESP32("on")
        }

        binding.btnTurnOff.setOnClickListener {
            sendCommandToESP32("off")
        }
    }

    private fun sendCommandToESP32(state: String) {
        // Use toHttpUrlOrNull() to parse URL safely
        val baseUrl = ESP32_BASE_URL.toHttpUrlOrNull()
        if (baseUrl == null) {
            Toast.makeText(requireContext(), "Invalid Base URL", Toast.LENGTH_SHORT).show()
            return
        }

        // Build the full URL with query parameters
        val url = baseUrl.newBuilder()
            .addPathSegment("relay")
            .addQueryParameter("state", state)
            .build()
            .toString()

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        Thread {
            try {
                val response = client.newCall(request).execute()
                val message = if (response.isSuccessful) {
                    "Command sent: $state"
                } else {
                    "Failed to send command"
                }
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
