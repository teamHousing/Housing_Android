package com.teamhousing.housing.ui.home.ask

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButtonState()
        editTextIsChanged(binding.edtMemoDirect)

        binding.btnMemoNext.setOnClickListener {
            (activity as AskActivity).replaceFragment(AskTimeFragment())
        }
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
                }
            }
        }
        binding.edtMemoDirect.setOnFocusChangeListener { _, chk ->
            if(chk) for(i in 0..3) buttonList[i].isChecked = false
        }
    }

    private fun editTextIsChanged(view : EditText){
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeRequestedTerm(s.toString())
            }
        })
    }
}