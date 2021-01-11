package com.teamhousing.housing.ui.calender

data class MonthData(
        val status: Int,
        val success: Boolean,
        val message: String,
        val data: CalendarData
) {
    data class CalendarData(
            // @SerializedName("notice")
            val noticeList: List<NoticeData>,
            // @SerializedName("issue")
            val promiseList: List<PromiseData>,
    )
}