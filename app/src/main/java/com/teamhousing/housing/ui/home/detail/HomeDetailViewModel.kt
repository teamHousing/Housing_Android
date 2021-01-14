package com.teamhousing.housing.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamhousing.housing.vo.InfoCommunicationListData

class HomeDetailViewModel : ViewModel() {
    private val _communicationList = MutableLiveData<MutableList<InfoCommunicationListData>>()
    val communicationList : LiveData<MutableList<InfoCommunicationListData>>
        get() = _communicationList

    fun setDummyCommunicationList(){
        val dummyCommunicationList = listOf(
                InfoCommunicationListData(
                        date = "2020. 08. 25",
                        time = "22 - 23시",
                        way = "집방문"
                ),
                InfoCommunicationListData(
                        date = "2020. 09. 25",
                        time = "13 - 14시",
                        way = "전화방문"
                ),
                InfoCommunicationListData(
                        date = "2020. 10. 25",
                        time = "22 - 23시",
                        way = "집방문"
                )
        )

        _communicationList.value = dummyCommunicationList.toMutableList()
    }
}