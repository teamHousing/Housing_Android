package com.teamhousing.housing.vo

data class CalendarData(
        val type: Int,
        val noticeId: Int?,
        val noticeTitle: String?,
        val noticeTime: String?,
        val issueId: Int?,
        val category: Int?,
        val solutionMethod: String?,
        val issueTitle: String?,
        val issueContents: String?,
        val promiseTime: String?
)