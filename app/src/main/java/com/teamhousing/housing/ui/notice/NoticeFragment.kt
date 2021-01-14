package com.teamhousing.housing.ui.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.size
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentHomeBinding
import com.teamhousing.housing.databinding.FragmentNoticeBinding
import com.teamhousing.housing.vo.NoticeData

class NoticeFragment : Fragment() {

    private lateinit var binding: FragmentNoticeBinding
    private lateinit var noticeAdapter: NoticeAdapter

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

        binding.rvNoticeItem.layoutManager = LinearLayoutManager(view.context)
        noticeAdapter.data = mutableListOf(
                NoticeData(
                        "11월 관리비 입금 안내",
                        "입금 계좌 : 1002455115135(우리은행/김미정) \n용돈 환영~! 카카오페이도 가능~!~!~! 이것도 최대 2줄이라네요 룰루라라ㅏ"
                ),
                NoticeData(
                        "11월 관리비 입금 안내",
                        "입금 계좌 : 1002455115135(우리은행/김미정) \n용돈 환영~! 카카오페이도 가능~!~!~! 이것도 최대 2줄이라네요 룰루라라ㅏ"
                ),
                NoticeData(
                    "수도꼭지가 고장났어요. 집이 물바다💧",
                    "입금 계좌 : 1002455115135(우리은행/김미정) \n용돈 환영~! 카카오페이도 가능~!~!~! 이것도 최대 2줄이라네요 우와아아"
                ),
                NoticeData(
                        "수도꼭지가 고장났어요. 집이 물바다💧",
                        "입금 계좌 : 1002455115135(우리은행/김미정) \n용돈 환영~! 카카오페이도 가능~!~!~! 이것도 최대 2줄이라네요 우와아아"
                )
        )
        noticeAdapter.notifyDataSetChanged()
    }
}