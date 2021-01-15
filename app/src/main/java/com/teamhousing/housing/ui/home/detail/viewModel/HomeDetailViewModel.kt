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
    private val _term = MutableLiveData<String>()
    val term : LiveData<String>
        get() = _term

    private val _photoList = MutableLiveData<MutableList<String>>()
    val photoList : LiveData<MutableList<String>>
        get() = _photoList

    private val _topInfo = MutableLiveData<AskItem>()
    val topInfo : LiveData<AskItem>
        get() = _topInfo

    private val _communicationList = MutableLiveData<MutableList<InfoCommunicationListData>>()
    val communicationList : LiveData<MutableList<InfoCommunicationListData>>
        get() = _communicationList

    fun getCommunicationDetail(id : Int){
        val call : Call<ResponseHomeDetailData> = HousingServiceImpl.service.getCommunicationDetail(
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwibmFtZSI6Iu2VmOyasOynhCIsImFkZHJlc3MiOiLshJzsmrjtirnrs4Tsi5wg7Jqp7IKw6rWsIO2VnOqwleuhnCAy6rCAIDEzNSIsInR5cGUiOjEsImlhdCI6MTYxMDY4MzQzOSwiZXhwIjoxNjExMjg4MjM5LCJpc3MiOiJjeWgifQ.GwqiDkwtI54GLdMyS6jGvWJkHThPiede_wy7fqvFjjc",
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
                        _topInfo.postValue(
                            AskItem(
                                it.data.id,
                                it.data.issue_title,
                                it.data.issue_contents,
                                it.data.progress,
                                it.data.category
                            )
                        )

                        _term.postValue(it.data.requested_term)
                        Log.d("요청사항", _term.value.toString())

                        val responseCommunicationList = mutableListOf<InfoCommunicationListData>()
                        for(item in it.data.promise_option){
                            Log.d("명",item.toString())
                            responseCommunicationList.apply {
                                add(
                                    InfoCommunicationListData(
                                        item[0],
                                        item[1],
                                        item[2]
                                    )
                                )
                            }
                        }

                        _communicationList.postValue(responseCommunicationList)
                        Log.d("명",_communicationList.value.toString())

                        val responsePhotoList = mutableListOf<String>()
                        for(item in it.data.issue_img){
                            responsePhotoList.apply {
                                add(
                                    item
                                )
                            }
                        }

                        _photoList.postValue(responsePhotoList)

                    } ?: showError(response.errorBody())
            }
        })
    }

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("홈 - 문의 상세 조회 오류", ob.toString())
    }

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