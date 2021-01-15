package com.teamhousing.housing.ui.home.detail.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.vo.AskItem
import com.teamhousing.housing.vo.InfoCommunicationListData
import com.teamhousing.housing.vo.ResponseHomeDetailData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailViewModel : ViewModel() {
    private val _topInfo = MutableLiveData<AskItem>()
    val topInfo : LiveData<AskItem>
        get() = _topInfo

    private val _communicationList = MutableLiveData<MutableList<InfoCommunicationListData>>()
    val communicationList : LiveData<MutableList<InfoCommunicationListData>>
        get() = _communicationList

    fun getCommunicationDetail(id : Int){
        val call : Call<ResponseHomeDetailData> = HousingServiceImpl.service.getCommunicationDetail(
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzUsIm5hbWUiOiLsnbTsp4TtmLgiLCJhZGRyZXNzIjoi6rK96riw64-EIOyViOyCsOyLnCDqs6DsnpTroZwxMTUiLCJ0eXBlIjoxLCJpYXQiOjE2MTA2NDQ4MTMsImV4cCI6MTYxMTI0OTYxMywiaXNzIjoiY3loIn0.rgAbXl5mkp-cDkx0v3qaoTQF_3mjm0ymKI4BHkokSag",
            id = id
        )
        call.enqueue(object : Callback<ResponseHomeDetailData> {
            override fun onFailure(call: Call<ResponseHomeDetailData>, t: Throwable) {
                // 통신 실패 로직
                Log.d("홈 - 문의 상세 조회 실패",t.toString())
            }
            override fun onResponse(
                call: Call<ResponseHomeDetailData>,
                response: Response<ResponseHomeDetailData>
            ) {
                response.takeIf { it.isSuccessful}
                    ?.body()
                    ?.let {
                        Log.d("명",it.data.toString())
                        _topInfo.value = AskItem(
                            it.data.id,
                            it.data.issue_title,
                            it.data.issue_contents,
                            it.data.progress,
                            it.data.category
                        )
                    } ?: showError(response.errorBody())
            }
        })
    }

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("홈 - 문의 상세 조회 오류", ob.toString())
    }
}