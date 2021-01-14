package com.teamhousing.housing.vo

data class RequestAskData(
    val category: Int,
    val is_promise: Boolean,
    val issue_contents: String,
    val issue_title: String,
    val requested_term: String
)