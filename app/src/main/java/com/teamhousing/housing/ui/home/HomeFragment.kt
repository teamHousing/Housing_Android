package com.teamhousing.housing.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEmojiTitle()
    }

    private fun initEmojiTitle(){
        binding.textView3.text = "뚝딱뚝딱"+getEmoji(0x1F6E0)+" 아직은 해결 중이에요!!"
    }

    fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}