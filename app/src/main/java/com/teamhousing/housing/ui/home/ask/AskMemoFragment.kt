package com.teamhousing.housing.ui.home.ask

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskMemoBinding

class AskMemoFragment : Fragment() {

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
                }
                binding.edtMemoDirect.clearFocus()
                buttonList[i].isChecked = !buttonList[i].isChecked
            }
        }
        binding.edtMemoDirect.setOnFocusChangeListener { _, chk ->
            if(chk){
                for(i in 0..3) buttonList[i].isChecked = false
            }
        }
    }
}