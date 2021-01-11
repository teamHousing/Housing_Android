package com.teamhousing.housing.ui.calender

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R

class DailyAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_calendar_promise, parent, false)
            return NoticeViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_calendar_notice, parent, false)
            return PromiseViewHolder(view)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        if (data[position] is NoticeData) {
            return 1
        } else {
            return 2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.onBind(data[position])
        // NoticeViewHolder, PromiseViewHolder 중 어떤 건지 안 알려줘도 되나?
    }
}