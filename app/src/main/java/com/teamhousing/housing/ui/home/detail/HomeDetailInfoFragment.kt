package com.teamhousing.housing.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentHomeDetailInfoBinding

class HomeDetailInfoFragment : Fragment() {
    private lateinit var binding : FragmentHomeDetailInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDetailInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        initEmojiTitle()

        return  binding.root
    }

    private fun initEmojiTitle(){
        binding.txtHomeDetailInfoAskTitle.text = getEmoji(0x1F6A8)+" 요청 사항"
        binding.txtHomeDetailInfoSubTitle.text = getEmoji(0x1F5E3)+" 소통 방식"
    }

    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}