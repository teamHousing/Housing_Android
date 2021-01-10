package com.teamhousing.housing.ui.calender

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import java.text.SimpleDateFormat
import java.util.Locale


class NoticeViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    private val title : TextView = itemView.findViewById(R.id.txt_title)
    private val time : TextView = itemView.findViewById(R.id.txt_time)
    private val timeFormat = SimpleDateFormat("a hh:mm", Locale.KOREA)

    fun onBind(data : NoticeData){

        val timeTxt = timeFormat.format(data.notice_time)
        title.text = data.notice_title
        time.text = timeTxt
    }
}