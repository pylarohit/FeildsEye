package com.rohit.feildseye.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.rohit.feildseye.R

class SoilQualityFragment : Fragment() {

    private lateinit var ecEditText: EditText
    private lateinit var ocEditText: EditText
    private lateinit var nEditText: EditText
    private lateinit var pEditText: EditText
    private lateinit var kEditText: EditText
    private lateinit var phEditText: EditText
    private lateinit var sEditText: EditText
    private lateinit var znEditText: EditText
    private lateinit var feEditText: EditText
    private lateinit var cuEditText: EditText
    private lateinit var mnEditText: EditText
    private lateinit var bEditText: EditText
    private lateinit var recommendButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_soil_quality, container, false)

        // Bind views
        ecEditText = view.findViewById(R.id.ec)
        ocEditText = view.findViewById(R.id.oc)
        nEditText = view.findViewById(R.id.n_ratio)
        pEditText = view.findViewById(R.id.p_ratio)
        kEditText = view.findViewById(R.id.k_ratio)
        phEditText = view.findViewById(R.id.ph_ratio)
        sEditText = view.findViewById(R.id.s)
        znEditText = view.findViewById(R.id.zn)
        feEditText = view.findViewById(R.id.fe)
        cuEditText = view.findViewById(R.id.cu)
        mnEditText = view.findViewById(R.id.mn)
        bEditText = view.findViewById(R.id.b)
        recommendButton = view.findViewById(R.id.recommendButton)

        // Set click listener for the recommend button
        recommendButton.setOnClickListener {
            checkSoilHealth()
        }

        return view
    }

    private fun checkSoilHealth() {
        // Collect data from the EditText fields
        val ec = ecEditText.text.toString().toDoubleOrNull() ?: 0.0
        val oc = ocEditText.text.toString().toDoubleOrNull() ?: 0.0
        val n = nEditText.text.toString().toDoubleOrNull() ?: 0.0
        val p = pEditText.text.toString().toDoubleOrNull() ?: 0.0
        val k = kEditText.text.toString().toDoubleOrNull() ?: 0.0
        val ph = phEditText.text.toString().toDoubleOrNull() ?: 0.0
        val s = sEditText.text.toString().toDoubleOrNull() ?: 0.0
        val zn = znEditText.text.toString().toDoubleOrNull() ?: 0.0
        val fe = feEditText.text.toString().toDoubleOrNull() ?: 0.0
        val cu = cuEditText.text.toString().toDoubleOrNull() ?: 0.0
        val mn = mnEditText.text.toString().toDoubleOrNull() ?: 0.0
        val b = bEditText.text.toString().toDoubleOrNull() ?: 0.0

        // Variables to determine overall suitability and health
        var isSuitableForCultivation = true
        val soilHealth = StringBuilder()

        // EC (Electrical Conductivity) check
        when {
            ec < 0.5 -> {
                soilHealth.append("Low EC: Soil may lack sufficient nutrients.\n")
                isSuitableForCultivation = false
            }
            ec > 2.0 -> {
                soilHealth.append("High EC: Soil salinity is too high.\n")
                isSuitableForCultivation = false
            }
        }

        // Organic Carbon (OC) check
        if (oc < 0.4 || oc > 1.0) {
            soilHealth.append("Organic Carbon level is not optimal.\n")
            isSuitableForCultivation = false
        }

        // Nitrogen (N) check
        if (n < 0.1 || n > 0.5) {
            soilHealth.append("Nitrogen level is not optimal.\n")
            isSuitableForCultivation = false
        }

        // Phosphorus (P) check
        if (p < 0.05 || p > 0.3) {
            soilHealth.append("Phosphorus level is not optimal.\n")
            isSuitableForCultivation = false
        }

        // Potassium (K) check
        if (k < 0.1 || k > 1.0) {
            soilHealth.append("Potassium level is not optimal.\n")
            isSuitableForCultivation = false
        }

        // pH check
        if (ph < 6.0 || ph > 7.0) {
            soilHealth.append("pH level is not suitable.\n")
            isSuitableForCultivation = false
        }

        // Sulfur (S) check
        if (s < 0.1 || s > 1.0) {
            soilHealth.append("Sulfur level is not optimal.\n")
            isSuitableForCultivation = false
        }

        // Micronutrients check
        val micronutrientStatus = arrayOf(
            zn to "Zinc", fe to "Iron", cu to "Copper", mn to "Manganese", b to "Boron"
        )
        micronutrientStatus.forEach { (level, nutrient) ->
            if (level < 0.1 || level > 1.0) {
                soilHealth.append("$nutrient level is not optimal.\n")
                isSuitableForCultivation = false
            }
        }

        // Determine final soil health message
        val suitabilityMessage = if (isSuitableForCultivation) {
            "The soil is suitable for cultivation."
        } else {
            "The soil is not suitable for cultivation."
        }

        // Show result in an AlertDialog
        showSoilHealthDialog(suitabilityMessage, soilHealth.toString())
    }

    private fun showSoilHealthDialog(suitabilityMessage: String, soilHealthDetails: String) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Soil Health Assessment")
        alertDialog.setMessage("$suitabilityMessage\n\n$soilHealthDetails")
        alertDialog.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.create().show()
    }
}
