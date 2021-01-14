package com.teamhousing.housing.ui.notice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityLoginBinding
import com.teamhousing.housing.databinding.ActivityNoticeDetailBinding
import com.teamhousing.housing.databinding.FragmentNoticeBinding
import com.teamhousing.housing.vo.NoticeData
import com.teamhousing.housing.vo.NoticeDetailData

class NoticeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeDetailBinding
    private lateinit var noticedetailAdapter: NoticeDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)

        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aContents = intent.getStringExtra("iContents")

        binding.tvNoticeDetailTitle.setText(aTitle)
        binding.tvNoticeDetailContent.setText(aContents)

        noticedetailAdapter = NoticeDetailAdapter(this)
        binding.rvNoticeCalendarItem.adapter = noticedetailAdapter
        binding.rvNoticeCalendarItem.layoutManager = LinearLayoutManager(this)

        noticedetailAdapter.data = mutableListOf(
            NoticeDetailData(
                "2020. 08. 25", "22-23시", "집방문"),

            NoticeDetailData("2020. 08. 25", "22-23시", "집방문"),
            NoticeDetailData(
                "2020. 08. 25", "22-23시", "집방문")
        )
        noticedetailAdapter.notifyDataSetChanged()
    }


}
