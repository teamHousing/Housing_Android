package com.teamhousing.housing.vo

data class ResponseHomeDetailData (
    val status: Int,
    val message: String,
    val data: DetailData
)

data class DetailData(
    val issue_img : List<String>,
    val promise_option : List<List<String>>,
    val confirmation_promise_option : List<String>,
    val id : Int,
    val category : Int,
    val issue_title : String,
    val issue_contents : String,
    val progress : Int,
    val requested_term : String,
    val promise_year : Int,
    val promise_month : Int,
    val promise_day : Int,
    val promise_time : String,
    val solution_method : String,
    val Replies : List<ReplyData>
)

data class ReplyData(
    val owner_status : List<Int>?,
    val user_status : List<Int>,
    val id : Int
)