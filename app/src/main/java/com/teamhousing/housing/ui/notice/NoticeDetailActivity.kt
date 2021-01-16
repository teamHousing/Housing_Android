package com.teamhousing.housing.ui.notice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityLoginBinding
import com.teamhousing.housing.databinding.ActivityNoticeDetailBinding
import com.teamhousing.housing.databinding.FragmentNoticeBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.NoticeData
import com.teamhousing.housing.vo.NoticeDetailData
import com.teamhousing.housing.vo.ResponseAddCalendarData
import com.teamhousing.housing.vo.ResponseNoticeData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeDetailBinding
    private lateinit var noticedetailAdapter: NoticeDetailAdapter

    fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = (e.string())
        Log.e("asd", ob)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)

        var intent = intent
        val aId = intent.getIntExtra("id",0)


        noticedetailAdapter = NoticeDetailAdapter(this)
        binding.rvNoticeCalendarItem.adapter = noticedetailAdapter

        val call : Call<ResponseAddCalendarData> = HousingServiceImpl.service.getAddCalendarNotice(
                token = UserTokenManager.getToken(this), id = aId)
        call.enqueue(object : Callback<ResponseAddCalendarData> {
            override fun onFailure(call: Call<ResponseAddCalendarData>, t: Throwable) {
                Log.e("실패", t.toString())
            }
            override fun onResponse(
                    call: Call<ResponseAddCalendarData>,
                    response: Response<ResponseAddCalendarData>
            ) {
                response.takeIf { it.isSuccessful}
                        ?.body()
                        ?.let {
                            var title = it.data.notice_title
                            var contents = it.data.notice_contents
                            var year = it.data.notice_year
                            var month = it.data.notice_month
                            var day = it.data.notice_day
                            var date = "${year}. ${month}. ${day}"
                            var times = it.data.notice_time.substring(0,1)+it.data.notice_time.substring(5,7) + "시"

                            binding.tvNoticeDetailTitle.text = title
                            binding.tvNoticeDetailContent.text = contents

                            var responseAddCalendarList = mutableListOf<NoticeDetailData>()
                            for(item in it.data.option){
                                responseAddCalendarList.apply{
                                    add(
                                            NoticeDetailData(
                                                    date, times
                                            )
                                    )
                                }
                            }
                            noticedetailAdapter.data = responseAddCalendarList
                            noticedetailAdapter.notifyDataSetChanged()
                        } ?: showError(response.errorBody())
            }
        }
        )
        binding.rvNoticeCalendarItem.layoutManager = LinearLayoutManager(this)
        noticedetailAdapter.notifyDataSetChanged()
    }
}
