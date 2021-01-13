package com.teamhousing.housing.ui.home.ask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamhousing.housing.vo.ContactData

class PromiseViewModel : ViewModel() {
    private val _promiseList = MutableLiveData<ArrayList<ContactData>>()
    val promiseList: LiveData<ArrayList<ContactData>>
        get() = _promiseList

    fun changePromiseList(list : ArrayList<ContactData>){
        _promiseList.value = list
    }
}