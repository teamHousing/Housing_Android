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
import java.util.*


class CalenderFragment : Fragment() {
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!
    val events = arrayListOf<EventDay>()
    private var dailyData: List<Any>? = null
    var allData: MutableMap<String, MutableList<CalendarData>> = hashMapOf()

    private lateinit var dailyAdapter: DailyAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        binding.calendar.setCalendarDayLayout(R.layout.item_calendar_cell)

        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
            }
        })

        dailyAdapter = DailyAdapter(requireContext())

        binding.rvDaily.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        dailyAdapter.data = mutableListOf(
                CalendarData(1, 2, "관리비내세요", "18-19",
                        null, null, null, null, null,
                        null),
                CalendarData(0, null, null, null, 1, 0, "직접 방문", "물콸콸",
                        "수리해주세요.", "18:00")
        )

        dailyAdapter.notifyDataSetChanged()


        val sampleN = ResponseCalendarData.Data.Notice(34, 2020, 12, 24, "hey", "you")
        val sampleN2 = ResponseCalendarData.Data.Notice(67, 2020, 12, 24, "hey", "you")
        val sampleP = ResponseCalendarData.Data.Promise(345, 2021, 1, 13, 0,
        "Yein", "직접 방문", "18:00", "visitiiiiii")
        val sampleP2 = ResponseCalendarData.Data.Promise(3458, 2020, 12, 24, 0,
                "Yein", "직접 방문", "18:00", "visitiiiiii")

        val sampleD = ResponseCalendarData.Data(listOf(sampleP,sampleP2), listOf(sampleN, sampleN2))
        calendarDataBind(sampleD)
        drawIcons()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        println(allData)
    }

    fun drawIcons(){
        for (i in allData.keys){
            var isNotice = false
            var isPromise = false
            var splitArr = i.split(".")
            var newDate = Calendar.getInstance()
            newDate.set(splitArr[0].toInt(), splitArr[1].toInt() - 1, splitArr[2].toInt())

            for ( j in allData[i]!!){
                if(j.type == 0){
                    isPromise = true
                }
                else{
                    isNotice = true
                }

                if(isPromise && isNotice){
                    break
                }
            }

            if (isNotice&&isPromise){events.add(EventDay(newDate, R.drawable.border_orange_blue_fill)) }
            else if(isNotice){events.add(EventDay(newDate, R.drawable.border_blue_fill_5)) }
            else if(isPromise){events.add(EventDay(newDate, R.drawable.border_orange_fill_5)) }

            binding.calendar.setEvents(events)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
