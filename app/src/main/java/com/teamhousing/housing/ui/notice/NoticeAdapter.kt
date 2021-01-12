package com.teamhousing.housing.ui.notice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.vo.NoticeData

class NoticeAdapter (private val context : Context) : RecyclerView.Adapter<NoticeViewHolder>(){
    var data = mutableListOf<NoticeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notice,
                parent, false)
        return NoticeViewHolder(view)
    }
    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}