package com.teamhousing.housing.ui.calender

data class Sample(
        val `data`: Data,
        val message: String,
        val status: Int,
        val success: Boolean
) {
    data class Data(
            // @SerializedName("issue")
            val promise: List<Promise>,
            val notice: List<Notice>
    ) {
        data class Promise(
                val category: Int,
                val contents: String,
                val day: Int,
                val id: Int,
                val isNotice: Int,
                val month: Int,
                // @SerializedName("solution_method")
                val solutionMethod: String,
                val time: String,
                val title: String,
                val year: Int
        )

        data class Notice(
                val day: Int,
                val id: Int,
                val month: Int,
                val title: String,
                val year: Int,
                val time: String
        )
    }
}