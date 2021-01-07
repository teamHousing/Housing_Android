package com.teamhousing.housing.ui.home.ask

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskContentBinding

class AskContentFragment() : Fragment() {

    private lateinit var binding: FragmentAskContentBinding
    private var buttonList = mutableListOf<CheckedTextView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_content, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButtonState()

        binding.btnContentNext.setOnClickListener {
            (activity as AskActivity).replaceFragment(AskPictureFragment())
        }
    }

    private fun changeButtonState() {
        binding.btnContentPromise.setOnClickListener {
            binding.btnContentPromise.isChecked = !binding.btnContentPromise.isChecked
            binding.btnContentPromiseNot.isChecked = false
        }
        binding.btnContentPromiseNot.setOnClickListener {
            binding.btnContentPromiseNot.isChecked = !binding.btnContentPromiseNot.isChecked
            binding.btnContentPromise.isChecked = false
        }

        buttonList = arrayListOf(binding.btnContentRepair, binding.btnContentContract,
            binding.btnContentFee, binding.btnContentNoise,
            binding.btnContentQuestion, binding.btnContentEtc)

        val boldFont = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_bold)
        val mediumFont = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_medium)

        for(i in 0..5){
            buttonList[i].setOnClickListener {
                for (j in 0..5) if(i != j){
                    buttonList[j].isChecked = false
                    buttonList[j].typeface = mediumFont
                    buttonList[j].setTextColor(Color.parseColor("#080808"))
                }
                buttonList[i].isChecked = !buttonList[i].isChecked
                if(buttonList[i].isChecked){
                    buttonList[i].typeface = boldFont
                    buttonList[i].setTextColor(Color.parseColor("#ffffff"))
                }else{
                    buttonList[i].typeface = mediumFont
                    buttonList[i].setTextColor(Color.parseColor("#080808"))
                }
            }
        }
    }
}