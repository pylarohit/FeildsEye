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
import pl.droidsonroids.gif.GifImageView

class RecommendFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_recommend, container, false)

        // Bind views
        nRatioEditText = view.findViewById(R.id.nRatio)
        pRatioEditText = view.findViewById(R.id.pRatio)
        kRatioEditText = view.findViewById(R.id.kRatio)
        phRatioEditText = view.findViewById(R.id.phRatio)
        temperatureEditText = view.findViewById(R.id.temprature)
        humidityEditText = view.findViewById(R.id.humidity)
        rainfallEditText = view.findViewById(R.id.rainfall)
        recommendButton = view.findViewById(R.id.recommendButton)

        // Set click listener on the button
        recommendButton.setOnClickListener {
            recommendCrop()
        }

        return view
    }

    private fun recommendCrop() {
        try {
            // Retrieve input values
            val nRatio = nRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val pRatio = pRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val kRatio = kRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val phRatio = phRatioEditText.text.toString().toDoubleOrNull() ?: 0.0
            val temperature = temperatureEditText.text.toString().toDoubleOrNull() ?: 0.0
            val humidity = humidityEditText.text.toString().toDoubleOrNull() ?: 0.0
            val rainfall = rainfallEditText.text.toString().toDoubleOrNull() ?: 0.0

            // Recommend crop based on conditions
            val recommendedCrop = when {
                nRatio in 1.0..2.0 && pRatio in 0.5..1.5 && kRatio in 1.0..2.5 && phRatio in 5.5..6.5 &&
                        temperature in 20.0..30.0 && humidity in 60.0..80.0 && rainfall in 100.0..200.0 -> "Wheat"
                nRatio in 1.5..3.0 && pRatio in 1.0..2.0 && kRatio in 1.5..3.0 && phRatio in 6.0..7.0 &&
                        temperature in 22.0..32.0 && humidity in 70.0..90.0 && rainfall in 200.0..300.0 -> "Rice"
                nRatio in 0.5..1.5 && pRatio in 0.5..1.0 && kRatio in 0.5..1.5 && phRatio in 6.5..7.5 &&
                        temperature in 25.0..35.0 && humidity in 50.0..70.0 && rainfall in 50.0..150.0 -> "Corn"
                else -> "No suitable crop found"
            }

            // Display the result in a dialog
            showDialog(recommendedCrop)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error in input values", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog(recommendedCrop: String) {
        // Inflate custom layout for the dialog
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.recomend_crop_dialog, null)
        val textView: TextView = dialogView.findViewById(R.id.text)
        val imageView: ImageView = dialogView.findViewById(R.id.setthis)

        // Set the recommended crop text
        textView.text = recommendedCrop

        // Set image based on the recommended crop
        when (recommendedCrop) {
            "Wheat" -> imageView.setImageResource(R.drawable.wheat)
            "Rice" -> imageView.setImageResource(R.drawable.rice)
            "Ceon" -> imageView.setImageResource(R.drawable.corn)
            else -> imageView.setImageResource(R.drawable.sorry)
        }

        // Build and show the dialog
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

}
