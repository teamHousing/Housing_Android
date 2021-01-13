package com.teamhousing.housing.ui.calender

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.vo.CalendarData

class DailyAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 하루치 데이터
    var data = mutableListOf<CalendarData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_calendar_promise, parent, false)
            return PromiseViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_calendar_notice, parent, false)
            return NoticeViewHolder(view)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].type
        // super.getItemViewType(position)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PromiseViewHolder -> holder.onBind(data[position])
            is NoticeViewHolder -> holder.onBind(data[position])
        }
    }
}