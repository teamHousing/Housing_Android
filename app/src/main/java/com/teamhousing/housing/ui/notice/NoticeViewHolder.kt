package com.teamhousing.housing.ui.notice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentNoticeBinding
import com.teamhousing.housing.vo.NoticeData

class NoticeViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    private val notice_Title : TextView = itemView.findViewById(R.id.tv_notice_title)
    private val notice_Contents : TextView = itemView.findViewById(R.id.tv_notice_content)
    fun onBind(data : NoticeData){
        notice_Title.text = data.title
        notice_Contents.text = data.contents
    }
}
