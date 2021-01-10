package com.teamhousing.housing.ui.calender

data class NoticeData(
        val id: Int,
        val notice_year: Int,
        val notice_month: Int,
        val notice_day: Int,
        val notice_title: String,
        val notice_time: String,
)