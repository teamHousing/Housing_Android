package com.teamhousing.housing.vo

data class ResponseAddCalendarData(
        val data: Data,
        val message: String,
        val status: Int,
        val success: Boolean
        ) {
    data class Data(
            val id: Int,
            val notice_title: String,
            val notice_contents: String,
            val notice_year: Int,
            val notice_month: Int,
            val notice_day: Int,
            val notice_time: String,
            val house_info_id: Int,
            val option: List<String>
    )
}