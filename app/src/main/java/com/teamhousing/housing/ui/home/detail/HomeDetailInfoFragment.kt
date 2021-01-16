package com.teamhousing.housing.ui.home.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamhousing.housing.databinding.FragmentHomeDetailInfoBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.ui.home.detail.adapter.InfoCommunicationListAdapter
import com.teamhousing.housing.ui.home.detail.adapter.InfoPhotoListAdapter
import com.teamhousing.housing.ui.home.detail.viewModel.HomeDetailViewModel
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.AskItem
import com.teamhousing.housing.vo.DetailInfo
import com.teamhousing.housing.vo.InfoCommunicationListData
import com.teamhousing.housing.vo.ResponseHomeDetailData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailInfoFragment : Fragment() {
    private lateinit var binding : FragmentHomeDetailInfoBinding
    private lateinit var infoCommunicationListAdapter: InfoCommunicationListAdapter
    private lateinit var infoPhotoListAdapter: InfoPhotoListAdapter
    private val homeDetailViewModel : HomeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDetailInfoBinding.inflate(inflater, container, false)
        binding.viewModel = homeDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initEmojiTitle()


        setCommunicationListAdapter()
        setPhotoListAdapter()
        getCommunicationDetail(UserTokenManager.getToken(requireContext()),(activity as HomeDetailActivity).id)

        return  binding.root
    }

    fun getCommunicationDetail(token : String, id : Int){
        val call : Call<ResponseHomeDetailData> = HousingServiceImpl.service.getCommunicationDetail(
            token = token,
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

                        binding.txtHomeDetailInfoTerm.text = it.data.requested_term

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

                        infoCommunicationListAdapter.data = responseCommunicationList
                        infoCommunicationListAdapter.notifyDataSetChanged()

                        val responsePhotoList = mutableListOf<String>()
                        for(item in it.data.issue_img){
                            responsePhotoList.apply {
                                add(
                                    item
                                )
                            }
                        }

                        Log.d("사진리스트",responsePhotoList.toString())

                        infoPhotoListAdapter.data = responsePhotoList
                        infoPhotoListAdapter.notifyDataSetChanged()


                    } ?: showError(response.errorBody())
            }
        })
    }

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("홈 - 문의 상세 조회 오류", ob.toString())
    }

    private fun setCommunicationListAdapter(){
        infoCommunicationListAdapter = InfoCommunicationListAdapter(requireContext())

        binding.rvHomeDetailCommunication.adapter = infoCommunicationListAdapter

    }

    private fun setPhotoListAdapter(){
        infoPhotoListAdapter = InfoPhotoListAdapter(requireContext())

        binding.rvHomeDetailPhoto.adapter = infoPhotoListAdapter

    }


    private fun initEmojiTitle(){
        binding.txtHomeDetailInfoAskTitle.text = getEmoji(0x1F6A8)+" 요청 사항"
        binding.txtHomeDetailInfoSubTitle.text = getEmoji(0x1F5E3)+" 소통 방식"
    }

    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}