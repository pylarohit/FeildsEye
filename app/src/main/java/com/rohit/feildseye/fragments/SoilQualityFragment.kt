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

        var isSuitableForCultivation = true
        val soilHealth = StringBuilder()

        // EC (Electrical Conductivity)
        soilHealth.append("EC: ${evaluateSoilCondition(ec, 0.5, 2.0)}\n")
        if (ec < 0.5 || ec > 2.0) isSuitableForCultivation = false

        // Organic Carbon (OC)
        soilHealth.append("OC: ${evaluateSoilCondition(oc, 0.4, 1.0)}\n")
        if (oc < 0.4 || oc > 1.0) isSuitableForCultivation = false

        // Nitrogen (N)
        soilHealth.append("N: ${evaluateSoilCondition(n, 0.1, 0.5)}\n")
        if (n < 0.1 || n > 0.5) isSuitableForCultivation = false

        // Phosphorus (P)
        soilHealth.append("P: ${evaluateSoilCondition(p, 0.05, 0.3)}\n")
        if (p < 0.05 || p > 0.3) isSuitableForCultivation = false

        // Potassium (K)
        soilHealth.append("K: ${evaluateSoilCondition(k, 0.1, 1.0)}\n")
        if (k < 0.1 || k > 1.0) isSuitableForCultivation = false

        // pH
        soilHealth.append("pH: ${evaluateSoilCondition(ph, 6.0, 7.0)}\n")
        if (ph < 6.0 || ph > 7.0) isSuitableForCultivation = false

        // Sulfur (S)
        soilHealth.append("S: ${evaluateSoilCondition(s, 0.1, 1.0)}\n")
        if (s < 0.1 || s > 1.0) isSuitableForCultivation = false

        // Micronutrients
        soilHealth.append("Zn: ${evaluateSoilCondition(zn, 0.1, 1.0)}\n")
        soilHealth.append("Fe: ${evaluateSoilCondition(fe, 0.1, 1.0)}\n")
        soilHealth.append("Cu: ${evaluateSoilCondition(cu, 0.1, 1.0)}\n")
        soilHealth.append("Mn: ${evaluateSoilCondition(mn, 0.1, 1.0)}\n")
        soilHealth.append("B: ${evaluateSoilCondition(b, 0.1, 1.0)}\n")

        val suitabilityMessage = if (isSuitableForCultivation) {
            "The soil is suitable for cultivation."
        } else {
            "The soil is not suitable for cultivation."
        }

        showSoilHealthDialog(suitabilityMessage, soilHealth.toString())
    }

    private fun evaluateSoilCondition(value: Double, min: Double, max: Double): String {
        return when {
            value < min -> "Poor"
            value in min..(min + (max - min) / 3) -> "Moderate"
            value in (min + (max - min) / 3)..(min + 2 * (max - min) / 3) -> "Good"
            value <= max -> "Excellent"
            else -> "Too High"
        }
    }

    private fun showSoilHealthDialog(suitabilityMessage: String, soilHealthDetails: String) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Soil Health Assessment")
        alertDialog.setMessage("$suitabilityMessage\n\nSoil Condition Breakdown:\n$soilHealthDetails")
        alertDialog.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.create().show()
    }
}
