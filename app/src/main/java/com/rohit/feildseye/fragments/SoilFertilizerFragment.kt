package com.rohit.feildseye.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rohit.feildseye.R

class SoilFertilizerFragment : Fragment() {

    // Define input fields
    private lateinit var soilTypeEditText: EditText
    private lateinit var cropNameEditText: EditText
    private lateinit var nRatioEditText: EditText
    private lateinit var pRatioEditText: EditText
    private lateinit var kRatioEditText: EditText
    private lateinit var phRatioEditText: EditText
    private lateinit var temperatureEditText: EditText
    private lateinit var humidityEditText: EditText
    private lateinit var rainfallEditText: EditText
    private lateinit var recommendButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_soil_fertilizer, container, false)

        // Initialize input fields
        soilTypeEditText = view.findViewById(R.id.soil_type)
        cropNameEditText = view.findViewById(R.id.crop_name)
        nRatioEditText = view.findViewById(R.id.n_ratio)
        pRatioEditText = view.findViewById(R.id.p_ratio)
        kRatioEditText = view.findViewById(R.id.k_ratio)
        phRatioEditText = view.findViewById(R.id.ph_ratio)
        temperatureEditText = view.findViewById(R.id.temprature)
        humidityEditText = view.findViewById(R.id.humidity)
        rainfallEditText = view.findViewById(R.id.rainfall)
        recommendButton = view.findViewById(R.id.recommendButton)

        // Set click listener for recommend button
        recommendButton.setOnClickListener {
            recommendFertilizer()
        }

        return view
    }

    private fun recommendFertilizer() {
        try {
            // Retrieve input values from the user
            val soilType = soilTypeEditText.text.toString().trim()
            val cropName = cropNameEditText.text.toString().trim()
            val nRatio = nRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val pRatio = pRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val kRatio = kRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val phRatio = phRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val temperature = temperatureEditText.text.toString().toDoubleOrNull() ?: 0.0
            val humidity = humidityEditText.text.toString().toDoubleOrNull() ?: 0.0
            val rainfall = rainfallEditText.text.toString().toDoubleOrNull() ?: 0.0

            // Check for valid input
            if (soilType.isEmpty() || cropName.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all the required information.", Toast.LENGTH_SHORT).show()
                return
            }

            // Recommend fertilizer based on the input values
            val recommendedFertilizer = when {
                soilType.equals("Loamy", ignoreCase = true) && cropName.equals("Wheat", ignoreCase = true) &&
                        nRatio in 1.0..2.0 && pRatio in 0.5..1.5 && kRatio in 1.0..2.5 && phRatio in 5.5..6.5 ->
                    "Fertilizer: NPK (Nitrogen-Phosphorus-Potassium) with 10-20-10 ratio for Wheat in Loamy soil"

                soilType.equals("Clayey", ignoreCase = true) && cropName.equals("Rice", ignoreCase = true) &&
                        nRatio in 1.5..3.0 && pRatio in 1.0..2.0 && kRatio in 1.5..3.0 && phRatio in 6.0..7.0 ->
                    "Fertilizer: NPK (Nitrogen-Phosphorus-Potassium) with 12-32-16 ratio for Rice in Clayey soil"

                soilType.equals("Sandy", ignoreCase = true) && cropName.equals("Corn", ignoreCase = true) &&
                        nRatio in 0.5..1.5 && pRatio in 0.5..1.0 && kRatio in 0.5..1.5 && phRatio in 6.5..7.5 ->
                    "Fertilizer: NPK (Nitrogen-Phosphorus-Potassium) with 15-15-15 ratio for Corn in Sandy soil"

                else -> "No suitable fertilizer found for the provided input."
            }

            // Display recommendation result in a dialog
            showDialog(recommendedFertilizer)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error processing input values", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog(recommendedFertilizer: String) {
        // Inflate dialog layout
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.recommend_fertilizer_dialog, null)
        val textView: TextView = dialogView.findViewById(R.id.fertitext)
        val imageView: ImageView = dialogView.findViewById(R.id.setthispic)

        // Set recommended fertilizer text
        textView.text = recommendedFertilizer

        // Set image based on the recommendation
        when (recommendedFertilizer) {
            "Fertilizer: NPK (Nitrogen-Phosphorus-Potassium) with 10-20-10 ratio for Wheat in Loamy soil" ->
                imageView.setImageResource(R.drawable.npk)
            "Fertilizer: NPK (Nitrogen-Phosphorus-Potassium) with 12-32-16 ratio for Rice in Clayey soil" ->
                imageView.setImageResource(R.drawable.npk2)
            else ->
                imageView.setImageResource(R.drawable.sorry)
        }

        // Build and show the dialog
        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
