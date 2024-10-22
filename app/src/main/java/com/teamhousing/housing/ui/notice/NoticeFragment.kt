package com.teamhousing.housing.ui.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentHomeBinding
import com.teamhousing.housing.databinding.FragmentNoticeBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.ui.login.LoginActivity
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.NoticeData
import com.teamhousing.housing.vo.RequestJoinData
import com.teamhousing.housing.vo.ResponseJoinData
import com.teamhousing.housing.vo.ResponseNoticeData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.file.Files.size

class NoticeFragment : Fragment() {

    private lateinit var binding: FragmentNoticeBinding
    private lateinit var noticeAdapter: NoticeAdapter

    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        //Toast.makeText(this, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoticeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
        //return inflater.inflate(R.layout.fragment_notice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noticeAdapter = NoticeAdapter(view.context)
        binding.rvNoticeItem.adapter = noticeAdapter
        val call : Call<ResponseNoticeData> = HousingServiceImpl.service.postHouseNotice(
                token = UserTokenManager.getToken(requireContext()))
        call.enqueue(object : Callback<ResponseNoticeData> {
            override fun onFailure(call: Call<ResponseNoticeData>, t: Throwable) {
                Log.e("error", t.toString())
            }
            override fun onResponse(
                    call: Call<ResponseNoticeData>,
                    response: Response<ResponseNoticeData>
            ) {
                response.takeIf { it.isSuccessful}
                        ?.body()
                        ?.let {
                            val responseNoticeList = mutableListOf<NoticeData>()
                            for(item in it.data.notice){
                                responseNoticeList.apply{
                                    add(
                                            NoticeData(
                                                    item.id,
                                                    item.notice_title,
                                                    item.notice_contents
                                            )
                                    )
                                }
                            }
                            noticeAdapter.data = responseNoticeList
                            noticeAdapter.notifyDataSetChanged()
                            binding.tvNoticeCnt.text = "("+noticeAdapter.data.size.toString()+")"
                            //binding.txtHomeAskCount.text = "("+askList.size.toString()+")"

                            //Log.e("NoticeFragment",responseNoticeList.toString())
                        } ?: showError(response.errorBody())
            }
        }
        )
        binding.rvNoticeItem.layoutManager = LinearLayoutManager(view.context)

        noticeAdapter.notifyDataSetChanged()
    }
}