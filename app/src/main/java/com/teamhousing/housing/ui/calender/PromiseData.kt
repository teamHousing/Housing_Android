package com.teamhousing.housing.ui.calender

data class PromiseData(
        val id: Int,
        val user_id: Int,
        val category: Int,
        val solution_method: String,
        val promise_year: Int,
        val promise_month: Int,
        val promise_day: Int,
        val issue_title : String,
        val issue_contents : String,
        val promise_time : String
        )