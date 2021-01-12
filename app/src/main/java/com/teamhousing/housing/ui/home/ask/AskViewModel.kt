package com.teamhousing.housing.ui.home.ask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AskViewModel : ViewModel() {

    private val _isPromise = MutableLiveData<Int>(-1)
    val isPromise: LiveData<Int>
        get() = _isPromise

    private val _category =  MutableLiveData<Int>(-1)
    val category: LiveData<Int>
        get() = _category

    private val _issueTitle = MutableLiveData<String>("")
    val issueTitle: LiveData<String>
        get() = _issueTitle

    private val _issueContents = MutableLiveData<String>("")
    val issueContents: LiveData<String>
        get() = _issueContents

    private val _requestedTerm = MutableLiveData<String>("")
    val requestedTerm: LiveData<String>
        get() = _requestedTerm

    fun changeIsPromise(chk: Int){
        _isPromise.value = chk
    }

    fun changeCategory(idx: Int){
        _category.value = idx
    }

    fun changeIssueTitle(str: String){
        _issueTitle.value = str
    }

    fun changeIssueContents(str: String){
        _issueContents.value = str
    }

    fun changeRequestedTerm(str: String){
        _requestedTerm.value = str
    }
}