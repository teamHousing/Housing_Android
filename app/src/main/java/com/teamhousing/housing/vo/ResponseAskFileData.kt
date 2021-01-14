package com.teamhousing.housing.vo

data class ResponseAskFileData(
    val `data`: Data,
    val message: String,
    val status: Int
) {
    data class Data(
        val issue_id: Int
    )
}