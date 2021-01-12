package com.teamhousing.housing.ui.home.ask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamhousing.housing.vo.PromiseData

class PromiseViewModel : ViewModel() {
    private val _promiseList = MutableLiveData<ArrayList<PromiseData>>()
    val promiseList: LiveData<ArrayList<PromiseData>>
        get() = _promiseList

    fun changePromiseList(list : ArrayList<PromiseData>){
        _promiseList.value = list
    }
}