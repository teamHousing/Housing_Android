package com.teamhousing.housing.ui.calender

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.vo.CalendarData


class NoticeViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    private val title : TextView = itemView.findViewById(R.id.txt_title)
    private val time : TextView = itemView.findViewById(R.id.txt_time)
    // private val inputFormat = SimpleDateFormat("KK:mm")
    // val outputFormat: DateFormat = SimpleDateFormat("a h:mm")

    fun onBind(data : CalendarData){

        // val timeTxt = inputFormat.parse(data.noticeTime.toString())
        title.text = data.noticeTitle
        time.text = data.noticeTime
    }
}