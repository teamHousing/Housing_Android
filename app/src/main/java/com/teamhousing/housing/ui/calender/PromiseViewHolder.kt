package com.teamhousing.housing.ui.calender

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import java.text.SimpleDateFormat
import java.util.*

class PromiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val category: TextView = itemView.findViewById(R.id.txt_category)
    private val title: TextView = itemView.findViewById(R.id.txt_title)
    private val how: TextView = itemView.findViewById(R.id.txt_how)
    private val time: TextView = itemView.findViewById(R.id.txt_time)
    private lateinit var categoryTxt: String
    private val timeFormat = SimpleDateFormat("a hh:mm", Locale.KOREA)

    fun onBind(data: PromiseData) {
        val timeTxt = timeFormat.format(data.promise_time)

        when (data.category) {
            0 -> {
                categoryTxt = "고장/수리"
            }
            1 -> {
                categoryTxt = "계약관련"
            }
            2 -> {
                categoryTxt = "요금납부"
            }
            3 -> {
                categoryTxt = "소음관련"
            }
            4 -> {
                categoryTxt = "문의사항"
            }
            else -> {
                categoryTxt = "그 외"
            }
        }
        category.text = categoryTxt

        title.text = data.issue_title
        how.text = data.solution_method
        time.text = timeTxt
    }
}