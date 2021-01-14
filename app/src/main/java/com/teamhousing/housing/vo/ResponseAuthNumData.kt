package com.teamhousing.housing.vo

data class ResponseAuthNumData(
        val `data`: Data,
        val message: String,
        val status: Int,
        val success: Boolean
){
    data class Data(
            val authentication_number : Int,
            val user_token: String,
            val type : Int
    )
}
