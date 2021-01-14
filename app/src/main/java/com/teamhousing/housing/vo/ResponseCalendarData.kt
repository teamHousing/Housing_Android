package com.teamhousing.housing.vo

import com.google.gson.annotations.SerializedName

data class ResponseCalendarData(
        val `data`: Data,
        val message: String,
        val status: Int,
        val success: Boolean
) {
    data class Data(
            @SerializedName("issue")
            val promise: List<Promise>,
            val notice: List<Notice>
    ) {
        data class Promise(
                val id: Int,
                val year: Int,
                val month: Int,
                val day: Int,
                val category: Int,
                val contents: String,
                @SerializedName("solution_method")
                val solutionMethod: String,
                val time: String,
                val title: String,
        )

        data class Notice(
                val id: Int,
                val year: Int,
                val month: Int,
                val day: Int,
                val title: String,
                val time: String
        )
    }
}