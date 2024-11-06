package com.rohit.feildseye.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rohit.feildseye.databinding.FragmentWaterPumpBinding

class WaterPumpFragment : Fragment() {

    private lateinit var binding: FragmentWaterPumpBinding

    companion object {
        private const val PHONE_NUMBER = "7993547924" // Replace with SIM900A number
        private const val SMS_PERMISSION_CODE = 101

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

        // Request SMS permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.SEND_SMS),
                SMS_PERMISSION_CODE
            )
        }

        // Set up button listeners
        binding.btnTurnOn.setOnClickListener {
            sendSMS("ON")
        }

        binding.btnTurnOff.setOnClickListener {
            sendSMS("OFF")
        }
    }

    private fun sendSMS(message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(PHONE_NUMBER, null, message, null, null)
            Toast.makeText(requireContext(), "SMS sent: $message", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "SMS failed to send.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(requireContext(), "SMS Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "SMS Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
