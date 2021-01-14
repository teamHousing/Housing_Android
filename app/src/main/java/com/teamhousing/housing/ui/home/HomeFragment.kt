package com.teamhousing.housing.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentHomeBinding
import com.teamhousing.housing.ui.home.adapter.HomeAskListAdapter
import com.teamhousing.housing.ui.home.ask.AskActivity
import com.teamhousing.housing.ui.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var askListAdapter: HomeAskListAdapter
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setAskListAdapter()
        onClickAskBtn()

        return binding.root
    }

    private fun setAskListAdapter(){
        askListAdapter = HomeAskListAdapter(requireContext())

        binding.rvHomeCompleteList.adapter = askListAdapter
        binding.rvHomeAskList.adapter = askListAdapter

        homeViewModel.setDummyAskList()

        homeViewModel.askList.observe(viewLifecycleOwner){ askList ->
            askListAdapter.replaceAskList(askList)
        }
    }

    private fun onClickAskBtn(){
        binding.btnHomeAsk.setOnClickListener {
            val askIntent = Intent(context, AskActivity::class.java)

            startActivity(askIntent)
        }

    }

}