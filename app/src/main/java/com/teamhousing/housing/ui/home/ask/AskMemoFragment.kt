package com.teamhousing.housing.ui.home.ask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskMemoBinding
import com.teamhousing.housing.util.ChangeButtonAttribute

class AskMemoFragment : Fragment() {

    private lateinit var binding: FragmentAskMemoBinding
    var buttonList = mutableListOf<Button>()

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
        val buttonListener = ChangeButtonAttribute()
        buttonListener.changeButtonState3(buttonList as ArrayList<Button>, binding.edtMemoDirect, 3)
    }
}