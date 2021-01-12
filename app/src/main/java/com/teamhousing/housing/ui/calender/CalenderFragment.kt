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
import com.teamhousing.housing.vo.CalendarData
import com.teamhousing.housing.vo.ResponseCalendarData
import kotlin.collections.MutableList as MutableList1


class CalenderFragment : Fragment() {
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    private var dailyData: List<Any>? = null
    var allData: MutableMap<String, MutableList1<CalendarData>> = hashMapOf()

    private lateinit var dailyAdapter: DailyAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val events = arrayListOf<EventDay>()
        binding.calendar.setCalendarDayLayout(R.layout.item_calendar_cell)

        dailyAdapter = DailyAdapter(requireContext())

        binding.rvDaily.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        dailyAdapter.data = mutableListOf(
                CalendarData(1, 2, "관리비내세요", "18-19",
                        null, null, null, null, null,
                        null, null),
                CalendarData(0, null, null, null, 1,
                        3648, 0, "직접 방문", "물콸콸",
                        "수리해주세요.", "18:00")
        )



        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                events.add(EventDay(eventDay.calendar, R.drawable.border_orange_blue_fill))
                binding.calendar.setEvents(events)
                dailyAdapter.notifyDataSetChanged()
            }
        })

    }

    fun calendarDataBind(data: ResponseCalendarData.Data) {
        for (notice in data.notice) {
            var year = notice.year
            var month = notice.month
            var day = notice.day
            var title = notice.title
            var time = notice.time

            var date = "${year}.${month}.${day}"
            var noticeModel : CalendarData = CalendarData(
                    type = 1,
                    noticeId = notice.id,
                    noticeTitle = title,
                    noticeTime = time,
                    issueId = null,
                    userID = null,
                    category = null,
                    solutionMethod = null,
                    issueTitle = null,
                    issueContents = null,
                    promiseTime = null
            )

            if(allData.containsKey(date)){
                allData[date]!!.add(noticeModel)
            }
            else{
                allData.put(date, mutableListOf(noticeModel))
            }

            for (promise in data.promise) {
                var year = promise.year
                var month = promise.month
                var day = promise.day
                var title = promise.title
                var time = promise.time
                var date = "${year}.${month}.${day}"

                var promiseModel : CalendarData = CalendarData(
                        type = 0,
                        noticeId = null,
                        noticeTitle = null,
                        noticeTime = null,
                        issueId = promise.id,
                        userID = promise.userId,
                        category = promise.category,
                        solutionMethod = promise.solutionMethod,
                        issueTitle = title,
                        issueContents = promise.contents,
                        promiseTime = time
                )

                if(allData.containsKey(date)){
                    allData[date]!!.add(promiseModel)
                }
                else{
                    allData.put(date, mutableListOf(promiseModel))
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
