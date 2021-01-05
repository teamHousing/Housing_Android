package com.teamhousing.housing.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentWriteMemoBinding
import com.teamhousing.housing.databinding.FragmentWritePictureBinding

class WritePictureFragment : Fragment() {

    private lateinit var binding: FragmentWritePictureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_picture, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPictureNext.setOnClickListener {
            (activity as WriteContactActivity).replaceFragment(WriteMemoFragment())
        }
    }
}