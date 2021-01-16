package com.teamhousing.housing.util


import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

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

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, res: String?) {
        GlideApp.with(view.context)
            .load(res)
            .transform(CenterCrop(), RoundedCorners(15))
            .into(view)

    }

    @JvmStatic
    @BindingAdapter("setReply")
    fun TextView.setReply(reply: Int) {
        text = when (reply) {
            0 -> {
                ""
            }
            1 -> {
                "약속이 확정되었어요"
            }
            2 -> {
                "문의사항을 확인했어요"
            }
            3 -> {
                "다시한번 약속해주세요"
            }
            else -> {
                "문의사항이 해결됐어요"
            }
        }
    }
}