package com.teamhousing.housing.vo

data class ResponseLoginData(
        val `data` : Data,
        val message : String,
        val status : Int
        ) {
    data class Data(
            val id: String,
            val type: Int,
            val user_token: String
    )
}
