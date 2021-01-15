package com.teamhousing.housing.vo

data class ResponseJoinData(
        val `data`: Data,
        val message: String,
        val status: Int,
        val success: Boolean
) {
    data class Data(
            val authentication_number : Int,
            val type : Int,
            val email : String,
            val password : String
    )
}