package com.teamhousing.housing.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.teamhousing.housing.databinding.FragmentHomeDetailInfoBinding
import com.teamhousing.housing.ui.home.detail.adapter.InfoCommunicationListAdapter

class HomeDetailInfoFragment : Fragment() {
    private lateinit var binding : FragmentHomeDetailInfoBinding
    private lateinit var infoCommunicationListAdapter: InfoCommunicationListAdapter
    private val homeDetailViewModel : HomeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDetailInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        initEmojiTitle()
        setCommunicationListAdapter()

        return  binding.root
    }

    private fun setCommunicationListAdapter(){
        infoCommunicationListAdapter = InfoCommunicationListAdapter(requireContext())

        binding.rvHomeDetailCommunication.adapter = infoCommunicationListAdapter

        homeDetailViewModel.setDummyCommunicationList()

        homeDetailViewModel.communicationList.observe(viewLifecycleOwner){ communicationList ->
            infoCommunicationListAdapter.replaceAskList(communicationList)

        }
    }

    private fun initEmojiTitle(){
        binding.txtHomeDetailInfoAskTitle.text = getEmoji(0x1F6A8)+" 요청 사항"
        binding.txtHomeDetailInfoSubTitle.text = getEmoji(0x1F5E3)+" 소통 방식"
    }

    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}