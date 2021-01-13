package com.teamhousing.housing.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object UserTokenManager {
    private lateinit var pref:SharedPreferences
    fun init(context: Context){
        pref = context.getSharedPreferences("Housing", Context.MODE_PRIVATE)
    }
    var token: String?
        get() = pref.getString("token", null)
        set(value) = pref.edit{
            it.putString("token",value)
    }

    private inline fun SharedPreferences.edit(
        operation:
        (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }
}
