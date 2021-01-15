package com.teamhousing.housing.vo

data class ResponseHomeAskListData (
    val status: Int,
    val message: String,
    val data: Data,
)

data class Data(
    val unit : String?,
    val complete_length : Int,
    val complete_list : List<AskItem>,
    val incomplete_length : Int,
    val incomplete_list : List<AskItem>
)

data class AskItem(
    val id : Int,
    val issue_title : String,
    val issue_contents : String,
    val progress : Int,
    val category : Int
)