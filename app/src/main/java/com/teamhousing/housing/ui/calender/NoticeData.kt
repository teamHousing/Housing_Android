package com.teamhousing.housing.ui.calender

data class NoticeData(
        val isNotice: Boolean,
        val id: Int,
        val year: Int,
        val month: Int,
        val day: Int,
        val notice_title: String,
        val notice_time: String,
) :