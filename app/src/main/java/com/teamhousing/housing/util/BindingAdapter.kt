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

    @JvmStatic
    @BindingAdapter("setCategory")
    fun TextView.setCategory(category: Int) {
        text = when (category) {
            0 -> {
                "고장 / 수리"
            }
            1 -> {
                "계약 관련"
            }
            2 -> {
                "요금 납부"
            }
            3 -> {
                "소음 관련"
            }
            4 -> {
                "거주 수칙 관련"
            }
            else -> {
                "그 외"
            }
        }
    }
}