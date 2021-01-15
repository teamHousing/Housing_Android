package com.teamhousing.housing.util

import android.content.Context
import android.content.SharedPreferences

object UserTokenManager {
    private lateinit var pref:SharedPreferences

    fun getToken(context: Context): String{
        val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token","")?:""
    }

    fun setToken(context: Context, token: String){
        val sharedPreferences = context.getSharedPreferences("token",Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString("token",token)
            .apply()
    }
}
