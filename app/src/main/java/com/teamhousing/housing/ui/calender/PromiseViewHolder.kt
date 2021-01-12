package com.teamhousing.housing.ui.calender

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.vo.CalendarData
import java.text.DateFormat
import java.text.SimpleDateFormat

class PromiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var category: TextView = itemView.findViewById(R.id.txt_category)
    var title: TextView = itemView.findViewById(R.id.txt_title)
    var how: TextView = itemView.findViewById(R.id.txt_how)
    var time: TextView = itemView.findViewById(R.id.txt_time)
    private lateinit var categoryTxt: String
    private val inputFormat = SimpleDateFormat("KK:mm")
    val outputFormat: DateFormat = SimpleDateFormat("a h:mm")

    fun onBind(data: CalendarData) {
        val timeTxt = inputFormat.parse(data.promiseTime)

        when (data.category) {
            0 -> {
                categoryTxt = "고장/수리"
            }
            1 -> {
                categoryTxt = "계약 관련"
            }
            2 -> {
                categoryTxt = "요금 납부"
            }
            3 -> {
                categoryTxt = "소음 관련"
            }
            4 -> {
                categoryTxt = "문의 사항"
            }
            else -> {
                categoryTxt = "그 외"
            }
        }

        category.text = categoryTxt


        title.text = data.issueTitle
        how.text = data.solutionMethod

        time.text = outputFormat.format(timeTxt)
    }
}