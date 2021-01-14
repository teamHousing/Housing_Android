package com.teamhousing.housing.ui.notice

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.vo.NoticeData
import com.teamhousing.housing.vo.NoticeDetailData

class NoticeDetailAdapter (private val context : Context) : RecyclerView.Adapter<NoticeDetailViewHolder>(){
    var data = mutableListOf<NoticeDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notice_detail,
                parent, false)

        return NoticeDetailViewHolder(view)
    }
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NoticeDetailViewHolder, position: Int) {
        holder.OnBind(data[position])
    }
}