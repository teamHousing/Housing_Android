package com.teamhousing.housing.util


import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setProgress")
    fun TextView.setProgress(progress: Int) {
        text = when (progress) {
            0 -> {
                "확인 전"
            }
            1 -> {
                "확인 중"
            }
            else -> {
                "해결 완료"
            }
        }
    }
}