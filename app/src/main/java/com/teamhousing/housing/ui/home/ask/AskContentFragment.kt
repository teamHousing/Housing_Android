package com.teamhousing.housing.ui.home.ask

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskContentBinding

class AskContentFragment() : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskContentBinding
    private var buttonList = mutableListOf<CheckedTextView>()

    private lateinit var boldFont: Typeface
    private lateinit var mediumFont: Typeface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_content, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        boldFont = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_bold)!!
        mediumFont = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_medium)!!

        changeButtonState()
        changeEditTextState()
        nextPage()
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.category.value != -1)
            buttonList[viewModel.category.value?.toInt()!!].setTextColor(Color.parseColor("#ffffff"))
    }

    private fun changeButtonState() {
        buttonList = arrayListOf(binding.btnContentRepair, binding.btnContentContract,
            binding.btnContentFee, binding.btnContentNoise,
            binding.btnContentQuestion, binding.btnContentEtc)

        binding.btnContentPromise.setOnClickListener {
            binding.btnContentPromise.isChecked = !binding.btnContentPromise.isChecked
            binding.btnContentPromiseNot.isChecked = false
            if(binding.btnContentPromise.isChecked)
                viewModel.changeIsPromise(true)
        }
        binding.btnContentPromiseNot.setOnClickListener {
            binding.btnContentPromiseNot.isChecked = !binding.btnContentPromiseNot.isChecked
            binding.btnContentPromise.isChecked = false
            if(binding.btnContentPromiseNot.isChecked)
                viewModel.changeIsPromise(false)
        }

        for(i in 0..5){
            buttonList[i].setOnClickListener {
                for (j in 0..5) if(i != j){
                    buttonList[j].isChecked = false
                    buttonList[j].typeface = mediumFont
                    buttonList[j].setTextColor(Color.parseColor("#080808"))
                    viewModel.changeCategory(-1)
                }
                buttonList[i].isChecked = !buttonList[i].isChecked
                if(buttonList[i].isChecked){
                    buttonList[i].typeface = boldFont
                    buttonList[i].setTextColor(Color.parseColor("#ffffff"))
                    viewModel.changeCategory(i)
                }else{
                    buttonList[i].typeface = mediumFont
                    buttonList[i].setTextColor(Color.parseColor("#080808"))
                    viewModel.changeCategory(-1)
                }
            }
        }
    }

    private fun changeEditTextState() {
        binding.edtContentTitle.setOnFocusChangeListener { _, chk ->
            if(chk){
                binding.edtContentTitle.setBackgroundResource(R.drawable.border_black_title_underline)
                binding.edtContentTitle.hint = ""
            }else{
                if(binding.edtContentTitle.text.isNullOrBlank()){
                    binding.edtContentTitle.setBackgroundResource(R.drawable.border_gray_title_underline)
                    binding.edtContentContent.hint = "제목을 작성해주세요."
                }
            }
        }
        editTextIsChanged(binding.edtContentTitle)
        editTextIsChanged(binding.edtContentContent)
    }

    private fun nextPage() {

        if(binding.btnContentPromise.isChecked && binding.btnContentPromiseNot.isChecked &&
            viewModel.category.value != -1 && !binding.edtContentTitle.text.isNullOrBlank() &&
                    !binding.edtContentContent.text.isNullOrBlank()
        ){
            binding.btnContentNext.isEnabled = true
        }

        binding.btnContentNext.setOnClickListener {
            (activity as AskActivity).replaceFragment(AskPictureFragment())
        }
    }

    private fun editTextIsChanged(view : EditText){
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(view == binding.edtContentTitle){
                    viewModel.changeIssueTitle(s.toString())
                    if (s.isNullOrBlank()){
                        view.typeface = mediumFont
                        view.setBackgroundResource(R.drawable.border_gray_title_underline)
                    }else{
                        view.typeface = boldFont
                        view.setBackgroundResource(R.drawable.border_black_title_underline)
                    }
                }else if(view == binding.edtContentContent){
                    viewModel.changeIssueContents(s.toString())
                }
            }
        })
    }
}