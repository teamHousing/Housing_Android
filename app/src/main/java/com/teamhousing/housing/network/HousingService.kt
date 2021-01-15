package com.teamhousing.housing.network

import com.teamhousing.housing.vo.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface HousingService {
    @POST("/user/login")
    fun postLogin(@Body body : RequestLoginData) : Call<ResponseLoginData>

    @POST("/authentication/confirm")
    fun postAuthCheck(@Body body : RequestAuthNumData) : Call<ResponseAuthNumData>
    @POST("user/registration/1")
    fun postJoin(@Body body : RequestJoinData) : Call<ResponseJoinData>

    @Multipart
    @POST("communication/image")
    fun postCommunicationFiles(
            @Header("jwt") token: String,
            @Part issueImages: List<MultipartBody.Part>
    ) : Call<ResponseAskFileData>

    @POST("communication/image")
    fun postCommunicationNoFiles(
            @Header("jwt") token: String
    ) : Call<ResponseAskFileData>

    @POST("communication/{id}")
    fun postCommunication(
            @Header("jwt") token: String,
            @Path("id") askId : Int,
            @Body body : RequestAskData
    ) : Call<ResponseAskData>

    @POST("communication/{id}/promise-option")
    fun postPromises(
            @Header("jwt") token: String,
            @Path("id") askId: Int,
            @Body body: RequestPromiseData
    ) : Call<ResponsePromiseData>

    @PUT("communication/{id}/promise-option")
    fun putPromises(
        @Header("jwt") token: String,
        @Path("id") askId: Int,
        @Body body: RequestPromiseData
    ) : Call<ResponsePromiseData>

    @GET("communication/{unit}")
    fun getCommunicationList(
        @Header("jwt") token: String,
        @Path("unit") unit: Int?
    ) : Call<ResponseHomeAskListData>

    @GET("/houseInfo")
    fun postHouseNotice(
            @Header("jwt") token: String,
    ) : Call<ResponseNoticeData>
}