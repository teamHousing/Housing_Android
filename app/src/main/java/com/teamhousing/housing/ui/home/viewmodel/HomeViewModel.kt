package com.teamhousing.housing.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamhousing.housing.vo.HomeAskListData

class HomeViewModel : ViewModel() {
    private val _askList = MutableLiveData<MutableList<HomeAskListData>>()
    val askList : LiveData<MutableList<HomeAskListData>>
        get() = _askList

    fun setDummyAskList(){
        val dummyAskList = listOf(
            HomeAskListData(
                category = "고장/수리",
                title = "밑에 층 집이 너무 시끄러워요... 하",
                contents = "집도 좋고 늘 빠르게 소통해주셔서 2년간 굉장히 만족하면서 생활했어요. 계약 기간이 끝나 가는데 다시 재계약을 하고 싶어요.",
                progress = 0
            ),
            HomeAskListData(
                category = "계약 관련",
                title = "이번달 계약 만료에 대해서 어쩌고 저쩌고",
                contents = "집도 좋고 늘 빠르게 소통해주셔서 2년간 굉장히 만족하면서 생활했어요. 계약 기간이 끝나 가는데 다시 재계약을 하고 싶어요.",
                progress = 1
            ),
            HomeAskListData(
                category = "요금 납부",
                title = "5일까지 관리비 입금바랍니다.",
                contents = "집도 좋고 늘 빠르게 소통해주셔서 2년간 굉장히 만족하면서 생활했어요. 계약 기간이 끝나 가는데 다시 재계약을 하고 싶어요.",
                progress = 2
            ),
            HomeAskListData(
                category = "그 외",
                title = "수도가 얼어서 집이 겨울왕국이 됐어요",
                contents = "집도 좋고 늘 빠르게 소통해주셔서 2년간 굉장히 만족하면서 생활했어요. 계약 기간이 끝나 가는데 다시 재계약을 하고 싶어요.",
                progress = 0
            )
        )

        _askList.value = dummyAskList.toMutableList()
    }
}