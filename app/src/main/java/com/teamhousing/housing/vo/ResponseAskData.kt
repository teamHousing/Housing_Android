package com.teamhousing.housing.vo

data class ResponseAskData(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val issue_id: Int
    )
}