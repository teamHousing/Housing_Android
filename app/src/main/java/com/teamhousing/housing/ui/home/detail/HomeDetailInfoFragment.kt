package com.teamhousing.housing.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentHomeDetailInfoBinding

class HomeDetailInfoFragment : Fragment() {
    private lateinit var binding : FragmentHomeDetailInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDetailInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return  binding.root
    }

}