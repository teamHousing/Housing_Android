package com.teamhousing.housing.ui.home.ask

import androidx.lifecycle.ViewModel
import com.teamhousing.housing.vo.ContactData

class AskViewModel : ViewModel() {

    private var _contactList = mutableListOf<ContactData>()
    val contactList: MutableList<ContactData>
        get() = _contactList

    init {
        var dataList = mutableListOf<ContactData>()
        _contactList = dataList
    }
}