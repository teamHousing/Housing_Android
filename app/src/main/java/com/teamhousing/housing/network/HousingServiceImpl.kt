package com.teamhousing.housing.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HousingServiceImpl {

    private const val BASE_URL = "http://3.34.74.249:3000/"
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service : HousingService = retrofit.create(HousingService::class.java)

}