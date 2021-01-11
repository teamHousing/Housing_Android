package com.teamhousing.housing.ui.calender

data class PromiseData(
        val isNotice: Boolean,
        val id: Int,
        val year: Int,
        val month: Int,
        val day: Int,
        val user_id: Int,
        val category: Int,
        val solution_method: String,
        val issue_title: String,
        val issue_contents: String,
        val promise_time: String
)

