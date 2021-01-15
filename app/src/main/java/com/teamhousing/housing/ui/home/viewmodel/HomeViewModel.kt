package com.teamhousing.housing.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.vo.AskItem
import com.teamhousing.housing.vo.ResponseHomeAskListData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    // 최초 여부
    private val _isEmpty = MutableLiveData<Boolean>(true)
    val isEmpty : LiveData<Boolean>
        get() = _isEmpty

    // 해결중인 문의사항 노출여부
    private val _isAskList = MutableLiveData<Boolean>(false)
    val isAskList : LiveData<Boolean>
        get() = _isAskList

    // 해결완료 문의사항 노출여부
    private val _isCompleteList = MutableLiveData<Boolean>(false)
    val isCompleteList : LiveData<Boolean>
        get() = _isCompleteList

    // 해결중인 문의사항 리스트
    private val _askList = MutableLiveData<MutableList<AskItem>>()
    val askList : LiveData<MutableList<AskItem>>
        get() = _askList

    // 해결완료 문의사항 리스트
    private val _completeList = MutableLiveData<MutableList<AskItem>>()
    val completeList : LiveData<MutableList<AskItem>>
        get() = _completeList

    fun getCommunicationList(){
        val call : Call<ResponseHomeAskListData> = HousingServiceImpl.service.getCommunicationList(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzUsIm5hbWUiOiLsnbTsp4TtmLgiLCJhZGRyZXNzIjoi6rK96riw64-EIOyViOyCsOyLnCDqs6DsnpTroZwxMTUiLCJ0eXBlIjoxLCJpYXQiOjE2MTA2NDQ4MTMsImV4cCI6MTYxMTI0OTYxMywiaXNzIjoiY3loIn0.rgAbXl5mkp-cDkx0v3qaoTQF_3mjm0ymKI4BHkokSag",
                unit = -1
        )
        call.enqueue(object : Callback<ResponseHomeAskListData> {
            override fun onFailure(call: Call<ResponseHomeAskListData>, t: Throwable) {
                // 통신 실패 로직
                Log.d("홈 - 소통하기 전체리스트 조회 실패",t.toString())
            }
            override fun onResponse(
                    call: Call<ResponseHomeAskListData>,
                    response: Response<ResponseHomeAskListData>
            ) {
                response.takeIf { it.isSuccessful}
                        ?.body()
                        ?.let {
                            // 해결중인 문의사항
                            val responseAskList = mutableListOf<AskItem>()
                            for(item in it.data.incomplete_list){
                                responseAskList.apply {
                                    add(
                                        AskItem(
                                            item.id,
                                            item.issue_title,
                                            item.issue_contents,
                                            item.progress,
                                            item.category,
                                        )
                                    )
                                }
                            }
                            _askList.postValue(responseAskList)

                            // 완료된 문의사항
                            val responseCompleteList = mutableListOf<AskItem>()
                            for(item in it.data.complete_list){
                                responseCompleteList.apply {
                                    add(
                                            AskItem(
                                                    item.id,
                                                    item.issue_title,
                                                    item.issue_contents,
                                                    item.progress,
                                                    item.category,
                                            )
                                    )
                                }
                            }
                            _completeList.postValue(responseCompleteList)

                            checkListSize(it.data.incomplete_length, it.data.complete_length)

                        } ?: showError(response.errorBody())
            }
        })
    }

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("홈 - 소통하기 전체리스트 조회 오류", ob.toString())
    }

    private fun checkListSize(askList : Int, completeList : Int){
        _isEmpty.value = false
        if(askList > 0 && completeList == 0){
            _isAskList.value = true
            _isCompleteList.value = false
        }
        else if(askList == 0 && completeList > 0){
            _isAskList.value = false
            _isCompleteList.value = true
        }
        else if(askList > 0 && completeList > 0){
            _isAskList.value = true
            _isCompleteList.value = true
        }
        else{
            _isEmpty.value = true
            _isAskList.value = false
            _isCompleteList.value = false
        }
    }
}