package com.teamhousing.housing.ui.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentCalenderBinding
import com.teamhousing.housing.databinding.FragmentHomeBinding

class CalenderFragment : Fragment() {
    private lateinit var binding: FragmentCalenderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalenderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}