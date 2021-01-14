package com.teamhousing.housing.network

import com.teamhousing.housing.vo.RequestLoginData
import com.teamhousing.housing.vo.ResponseCalendarData
import com.teamhousing.housing.vo.ResponseLoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface HousingService {
    @Headers("Content-Type:application/json")
    @POST("/user/login")
    fun postLogin(@Body body : RequestLoginData) : Call<ResponseLoginData>

    @POST("/calendar/schedule")
    fun postCalendar(
            @Header("jwt") token: String?
    ) : Call<ResponseCalendarData>
}