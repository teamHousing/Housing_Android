package com.teamhousing.housing.contact

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentWriteContentBinding
import com.teamhousing.housing.databinding.FragmentWriteMemoBinding

class WriteMemoFragment : Fragment() {

    private lateinit var binding: FragmentWriteMemoBinding
    var buttonList = mutableListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_memo, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButtonState()

        binding.btnMemoNext.setOnClickListener {
            (activity as WriteContactActivity).replaceFragment(WriteTimeFragment())
        }
    }

    private fun changeButtonState() {
        buttonList = arrayListOf(binding.btnMemoThank, binding.btnMemoQuick,
            binding.btnMemoPrecontact, binding.btnMemoAbsence)
        val buttonListener = ChangeButtonAttributeListener()
        buttonListener.changeButtonState3(buttonList as ArrayList<Button>, binding.etMemoDirect, 3)
    }
}