package com.teamhousing.housing.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentCalenderBinding
import java.util.*

class CalenderFragment : Fragment() {
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    private var date : Date? = null
    private var dailyData : List<Any>? = null
    private var totalData : HashMap<Date, List<Any>>? = null

    private lateinit var dailyAdapter: DailyAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val view = binding.root
        dailyAdapter = DailyAdapter(requireContext())

        val events = arrayListOf<EventDay>()
        binding.calendar.setCalendarDayLayout(R.layout.item_calendar_cell)

        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                events.add(EventDay(eventDay.calendar, R.drawable.border_orange_blue_fill))
                binding.calendar.setEvents(events)
            }
        })

        binding.rvDaily.apply{
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        dailyAdapter.data = mutableListOf(
                NoticeData(
                        true,
                         1,
                         2021,
                        1,
                        28,
                        "관리비 납부 안내",
                        "18:00"
                ),
                PromiseData(
                        false,
                        1, 2021, 7, 27, 123, 0,
                        "직접 방문", "수도꼭지가 고장났어요.",
                        "일주일째 못 씻고있어요, 간지러워요.",
                        "21:00"
                )
        )

        dailyAdapter.notifyDataSetChanged()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}