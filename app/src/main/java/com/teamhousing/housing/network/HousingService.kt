package com.teamhousing.housing.network

import com.teamhousing.housing.vo.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface HousingService {

//    @Headers("Content-Type:application/json")
//    @POST("users/signin")
//    fun postLogin(
//        @Body body : RequestLoginData
//    ) : Call<ResponseLoginData>

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
}