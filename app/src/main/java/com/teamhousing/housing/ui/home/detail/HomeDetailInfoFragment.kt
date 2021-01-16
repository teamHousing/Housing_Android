package com.teamhousing.housing.ui.home.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.teamhousing.housing.databinding.FragmentHomeDetailInfoBinding
import com.teamhousing.housing.ui.home.detail.adapter.InfoCommunicationListAdapter
import com.teamhousing.housing.ui.home.detail.adapter.InfoPhotoListAdapter
import com.teamhousing.housing.ui.home.detail.viewModel.HomeDetailViewModel

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
//        setPhotoListAdapter()
//        setDetailInfo()

        return  binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getInfo()
    }

    private fun setCommunicationListAdapter(){
        infoCommunicationListAdapter = InfoCommunicationListAdapter(requireContext())

        binding.rvHomeDetailCommunication.adapter = infoCommunicationListAdapter

//        homeDetailViewModel.setDummyCommunicationList()

        homeDetailViewModel.communicationList.observe(viewLifecycleOwner){ communicationList ->
            infoCommunicationListAdapter.replaceCommunicationList(communicationList)

        }

    }
//
//    private fun setPhotoListAdapter(){
//        infoPhotoListAdapter = InfoPhotoListAdapter(requireContext())
//
//        binding.rvHomeDetailPhoto.adapter = infoPhotoListAdapter
//        homeDetailViewModel.photoList.observe(viewLifecycleOwner){ photoList ->
//            infoPhotoListAdapter.replacePhotoList(photoList)
//
//        }
//    }

    private fun getInfo(){
        Log.d("이거", homeDetailViewModel.term.value.toString())
        binding.txtHomeDetailInfoTerm.text = homeDetailViewModel.term.value
    }


    private fun initEmojiTitle(){
        binding.txtHomeDetailInfoAskTitle.text = getEmoji(0x1F6A8)+" 요청 사항"
        binding.txtHomeDetailInfoSubTitle.text = getEmoji(0x1F5E3)+" 소통 방식"
    }

    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}