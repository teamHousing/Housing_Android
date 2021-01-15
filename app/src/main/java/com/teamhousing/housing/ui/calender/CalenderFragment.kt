package com.teamhousing.housing.ui.calender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentCalenderBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.CalendarData
import com.teamhousing.housing.vo.ResponseCalendarData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CalenderFragment : Fragment() {
    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!
    val today = Calendar.getInstance()
    val events = arrayListOf<EventDay>()
    var allData: HashMap<String, MutableList<CalendarData>> = hashMapOf()
    private lateinit var dailyAdapter: DailyAdapter

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = e.string()
        Log.d("errorMessage", ob)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        binding.calendar.setCalendarDayLayout(R.layout.item_calendar_cell)

        connectSever()
        setDateText(today)

        dailyAdapter = DailyAdapter(requireContext())
        binding.rvDaily.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                setRecyclerView(eventDay.calendar)
                setDateText(eventDay.calendar)
            }
        })

        return binding.root
    }

    fun setRecyclerView(targetDay : Calendar){
        var tempData : List<CalendarData>?
        tempData = getDailyData(targetDay)
        tempData?.let { dailyAdapter.data = it } ?: run { dailyAdapter.data = emptyList() }
        dailyAdapter.notifyDataSetChanged()

        Log.d("today", tempData.toString())
    }

    fun connectSever(){

        val token = UserTokenManager.getToken(requireContext())
        val call : Call<ResponseCalendarData> = HousingServiceImpl.service.postCalendar(
                token
        )

        call.enqueue(object : Callback<ResponseCalendarData>{
            override fun onFailure(call: Call<ResponseCalendarData>, t: Throwable) {
                Toast.makeText(context, "서버가 닫혀있어요!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseCalendarData>, response: Response<ResponseCalendarData>) {
                response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let { it ->
                            calendarDataBind(it.data)
                            drawIcons()
                            setRecyclerView(today)
                        } ?: showError(response.errorBody())
            }
        })
    }
    fun calendarDataBind(data: ResponseCalendarData.Data) {

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
        for (notice in data.notice) {
            var year = notice.year
            var month = notice.month
            var day = notice.day
            var title = notice.title
            var time = notice.time

            var date = "${year}.${month}.${day}"
            var noticeModel: CalendarData = CalendarData(
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

            if (allData.containsKey(date)) {
                allData[date]!!.add(noticeModel)
            } else {
                allData.put(date, mutableListOf(noticeModel))
            }
        }

        Log.d("allData: ", allData.toString())
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

    fun getDailyData(clickedDay : Calendar) : List<CalendarData>? {
        var year = clickedDay.get(Calendar.YEAR).toString()
        var month = clickedDay.get(Calendar.MONTH) + 1
        month.toString()
        var day = clickedDay.get(Calendar.DATE).toString()
        var keyDate = "$year.$month.$day"

        Log.d("getDailyData: ", allData[keyDate].toString())

        allData[keyDate]?.let{return allData[keyDate]} ?: run {return emptyList()}

    }

    fun setDateText(clickedDay: Calendar){
        binding.txtDate.text = "${clickedDay.get(Calendar.MONTH) + 1}월 ${clickedDay.get(Calendar.DAY_OF_MONTH)}일의 일정"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
