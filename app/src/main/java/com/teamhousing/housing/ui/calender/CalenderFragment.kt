package com.teamhousing.housing.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentCalenderBinding

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

        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                events.add(EventDay(eventDay.calendar, R.drawable.border_orange_blue_fill))
                binding.calendar.setEvents(events)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}