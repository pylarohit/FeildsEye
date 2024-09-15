package com.rohit.feildseye.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohit.feildseye.R
import com.rohit.feildseye.databinding.FragmentAgriloansBinding

class AgriloansFragment : Fragment() {

    private var binding: FragmentAgriloansBinding? = null
    private lateinit var agriloansAdapter: AgriloansAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val loansList = arrayListOf(
        "National Mission For Sustainable Agriculture",
        "Pradhan Mantri Fasal Bima Yojana",
        "Soil Health Management Scheme",
        "Paramparagat Krishi Vikas Yojana"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgriloansBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        agriloansAdapter = AgriloansAdapter(loansList)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.rcyclrLoansList?.layoutManager = linearLayoutManager
        binding?.rcyclrLoansList?.adapter = agriloansAdapter

        agriloansAdapter.setOnItemClickListener(object : AgriloansAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> findNavController().navigate(R.id.action_agriloansFragment_to_yojnaFragment)
                    1 -> findNavController().navigate(R.id.action_agriloansFragment_to_yojnaFragment1)
                    2 -> findNavController().navigate(R.id.action_agriloansFragment_to_yojnaFragment2)
                    3 -> findNavController().navigate(R.id.action_agriloansFragment_to_yojnaFragment3)
                }
            }
        })
    }
}
