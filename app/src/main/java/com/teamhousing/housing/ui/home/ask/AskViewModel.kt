package com.teamhousing.housing.ui.home.ask

import androidx.lifecycle.ViewModel
import com.teamhousing.housing.vo.ContactData

class AskViewModel : ViewModel() {

    private var _contactList = mutableListOf<ContactData>()
    val contactList: MutableList<ContactData>
        get() = _contactList


    init {
        var dataList = mutableListOf<ContactData>()
        dataList.add(ContactData("2021. 01. 02", "16-17시", "집 방문"))
        dataList.add(ContactData("2021. 01. 05", "22-23시", "전화방문"))
        _contactList = dataList
    }
}