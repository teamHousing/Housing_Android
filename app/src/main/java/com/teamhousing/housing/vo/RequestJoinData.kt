package com.teamhousing.housing.vo

data class RequestJoinData(
        val authentication_number : Int,
        val user_name : String,
        val age : Int,
        val email : String,
        val password : String
)
