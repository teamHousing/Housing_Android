package com.teamhousing.housing.ui.notice

import android.content.Context
import android.content.Intent
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
        holder.itemView.setOnClickListener(){
            val nData = data.get(position)

            var gTitle: String = nData.title
            var gContents: String = nData.contents

            val intent = Intent(context, NoticeDetailActivity::class.java)

            intent.putExtra("iTitle", gTitle)
            intent.putExtra("iContents", gContents)

            context.startActivity(intent)
        }
    }
}