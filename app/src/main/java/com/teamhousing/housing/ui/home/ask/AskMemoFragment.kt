package com.teamhousing.housing.ui.home.ask

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskMemoBinding

class AskMemoFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskMemoBinding
    private var buttonList = mutableListOf<CheckedTextView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_memo, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(viewModel.isPromise.value){
            0 -> binding.btnMemoNext.text = "다음 단계"
            1 -> binding.btnMemoNext.text = "등록하기"
        }

        changeButtonState()
        editTextIsChanged(binding.edtMemoDirect)

        nextPage()
    }

    private fun changeButtonState() {
        buttonList = arrayListOf(binding.btnMemoThank, binding.btnMemoQuick,
            binding.btnMemoPrecontact, binding.btnMemoAbsence)

        for(i in 0..3){
            buttonList[i].setOnClickListener {
                for (j in 0..3) if(i != j){
                    buttonList[j].isChecked = false
                    viewModel.changeRequestedTerm("")
                }
                binding.edtMemoDirect.clearFocus()
                buttonList[i].isChecked = !buttonList[i].isChecked
                if(buttonList[i].isChecked){
                    viewModel.changeRequestedTerm(buttonList[i].text.toString())
                    binding.btnMemoNext.isEnabled = true
                }else {
                    binding.btnMemoNext.isEnabled = false
                }
            }
        }
        binding.edtMemoDirect.setOnFocusChangeListener { _, chk ->
            if(chk) for(i in 0..3)  {
                buttonList[i].isChecked = false
                binding.btnMemoNext.isEnabled = !binding.edtMemoDirect.text.isNullOrBlank()
            }
            else binding.btnMemoNext.isEnabled = false
        }
    }

    private fun editTextIsChanged(view : EditText){
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeRequestedTerm(s.toString())
                binding.btnMemoNext.isEnabled = !s.isNullOrBlank()
            }
        })
    }


    private fun nextPage() {
        binding.btnMemoNext.setOnClickListener {
            when(viewModel.isPromise.value){
                0 -> {
                    val intent = Intent(requireContext(), PromiseActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    // 서버 전송
                }

            }
            // 홈으로 돌아가기
//          val intent = Intent()
//          setResult(Activity.RESULT_OK, intent)
        }
    }
}