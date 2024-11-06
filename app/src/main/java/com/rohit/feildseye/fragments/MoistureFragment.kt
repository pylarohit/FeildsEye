package com.rohit.feildseye.fragments

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rohit.feildseye.databinding.FragmentMoistureBinding

class MoistureFragment : Fragment() {

    private var binding: FragmentMoistureBinding? = null
    private val PHONE_NUMBER = "7993547924" // Replace with the SIM900A number
    private val REQUEST_CODE_SMS_PERMISSION = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoistureBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Request SMS permissions if not already granted
        requestSmsPermissions()

        binding?.checkMoistureButton?.setOnClickListener {
            requestMoistureData()
        }
    }

    private fun requestSmsPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }

    private fun requestMoistureData() {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(PHONE_NUMBER, null, "CHECK_MOISTURE", null, null)
            Toast.makeText(requireContext(), "Requesting moisture data...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to request moisture data.", Toast.LENGTH_SHORT).show()
        }
    }

    // BroadcastReceiver to handle incoming SMS messages
    private val smsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>
                for (pdu in pdus) {
                    val message = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = message.displayOriginatingAddress
                    if (sender == PHONE_NUMBER) {
                        val moistureData = message.messageBody
                        displayMoistureData(moistureData)
                    }
                }
            }
        }
    }

    private fun displayMoistureData(data: String) {
        // Assuming the data is a percentage, e.g., "Moisture: 75%"
        val moisturePercentage = data.replace("[^0-9]".toRegex(), "").toIntOrNull() ?: 0
        binding?.moistureLevelTextView?.text = "$moisturePercentage%"
        binding?.moistureProgressBar?.progress = moisturePercentage
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(smsReceiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(smsReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
