package com.teamhousing.housing.ui.notice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.vo.NoticeDetailData

class NoticeDetailViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    private val notice_date : TextView = itemView.findViewById(R.id.tv_date)
    private val notice_time : TextView = itemView.findViewById(R.id.tv_time)

    fun OnBind(data : NoticeDetailData){
        notice_date.text = data.date
        notice_time.text = data.time
    }
}
