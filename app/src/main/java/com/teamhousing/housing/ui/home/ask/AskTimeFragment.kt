package com.teamhousing.housing.ui.home.ask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskTimeBinding
import com.teamhousing.housing.util.ChangeButtonAttribute

class AskTimeFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskTimeBinding
    var buttonList = mutableListOf<ConstraintLayout>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButtonState()
        makeContact()
    }

    private fun changeButtonState() {
        buttonList = arrayListOf(binding.btnTimeMeet, binding.btnTimeCall)
        val buttonListener = ChangeButtonAttribute()
        buttonListener.changeButtonState2(buttonList as ArrayList<ConstraintLayout>, 1)
    }

    private fun makeContact() {
        var adapter = ContactAdapter()
        adapter.data = viewModel.contactList

        binding.rvTime.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}