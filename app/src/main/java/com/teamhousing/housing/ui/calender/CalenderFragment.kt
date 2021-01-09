package com.teamhousing.housing.ui.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applandeo.materialcalendarview.EventDay
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentCalenderBinding
import com.teamhousing.housing.databinding.FragmentHomeBinding
import java.util.*

class CalenderFragment : Fragment() {
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val events = arrayListOf<EventDay>()
        binding.calendar.setCalendarDayLayout(R.layout.item_calendar_cell)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}