package com.teamhousing.housing.vo

data class ResponseNoticeData(
        val data: HouseInfoList,
        val message: String,
        val status: Int,
        val success: Boolean
)

data class HouseInfoList(
        val houseInfo : HouseInfoItem,
        val notice : List<NoticeItem>
)

data class HouseInfoItem(
        val hope_time: List<String>,
        val id: Int,
        val owner_name: String,
        val profile_message: String,
        val profile_image: String,
        val response_time: String
)

data class NoticeItem(
        val id : Int,
        val notice_title : String,
        val notice_contents : String
)
