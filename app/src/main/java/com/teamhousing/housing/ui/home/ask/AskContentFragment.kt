package com.teamhousing.housing.ui.home.ask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskContentBinding
import com.teamhousing.housing.util.ChangeButtonAttribute

class AskContentFragment() : Fragment() {

    private lateinit var binding: FragmentAskContentBinding
    private var buttonList = mutableListOf<Button>()
    private var buttonList2 = mutableListOf<ConstraintLayout>()


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
        buttonList = arrayListOf(binding.btnContentRepair, binding.btnContentContract,
            binding.btnContentFee, binding.btnContentNoise,
            binding.btnContentQuestion, binding.btnContentEtc)
        buttonList2 = arrayListOf(binding.btnContentPromise, binding.btnContentPromiseNot)
        val buttonListener = ChangeButtonAttribute()
        buttonListener.changeButtonState(buttonList as ArrayList<Button>, 5, 0)
        buttonListener.changeButtonState2(buttonList2 as ArrayList<ConstraintLayout>, 1)
    }
}