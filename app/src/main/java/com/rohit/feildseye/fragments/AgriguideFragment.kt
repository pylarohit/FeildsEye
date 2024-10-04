package com.rohit.feildseye.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohit.feildseye.R
import com.rohit.feildseye.databinding.FragmentAgriguideBinding

class AgriguideFragment : Fragment() {

    private var binding: FragmentAgriguideBinding? = null
    lateinit var EcommerceAdapter: EcommerceAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val ItemsList = arrayListOf(
        "Agriculture Tractor",
        "Agriculture Fertilizer",
        "Mild Steel Tractor Cultivator",
        "Agriculture Drone",
        "Agriculture Seeder"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgriguideBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EcommerceAdapter = EcommerceAdapter(ItemsList)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.ecommrcyclr?.layoutManager = linearLayoutManager
        binding?.ecommrcyclr?.adapter = EcommerceAdapter

        binding?.cat1?.setOnClickListener {
            findNavController().navigate(R.id.action_agriguideFragment_to_RecommendFragment)
        }
        binding?.cat2?.setOnClickListener {
            findNavController().navigate(R.id.action_agriguideFragment_to_SoilFertilizerFragment)
        }
        binding?.cat3?.setOnClickListener {
            findNavController().navigate(R.id.action_agriguideFragment_to_SoilQualityFragment)
        }


        EcommerceAdapter.setOnItemClickListener(object : EcommerceAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProductFragment)
                    1 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct1Fragment)
                    2 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct2Fragment)
                    3 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct3Fragment)
                    4 -> findNavController().navigate(R.id.action_agriguideFragment_to_ecomProduct4Fragment)
                }
            }
        })
    }
}
