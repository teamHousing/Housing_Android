package com.teamhousing.housing.ui.notice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentNoticeBinding
import com.teamhousing.housing.vo.NoticeData
import com.teamhousing.housing.vo.NoticeDetailData

class NoticeDetailViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    private val notice_day : TextView = itemView.findViewById(R.id.tv_notice_detail_day)
    private val notice_time : TextView = itemView.findViewById(R.id.tv_notice_detail_time)
    fun OnBind(data : NoticeDetailData){
        notice_day.text = data.day
        notice_time.text = data.time
    }
}
