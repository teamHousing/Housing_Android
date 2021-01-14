package com.teamhousing.housing.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.teamhousing.housing.databinding.FragmentHomeBinding
import com.teamhousing.housing.ui.home.adapter.HomeAskListAdapter
import com.teamhousing.housing.ui.home.ask.AskActivity
import com.teamhousing.housing.ui.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var askListAdapter: HomeAskListAdapter
    private lateinit var completeListAdapter: HomeAskListAdapter
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setAskListAdapter()
        onClickAskBtn()

        return binding.root
    }

    private fun setAskListAdapter(){
        askListAdapter = HomeAskListAdapter(requireContext())
        completeListAdapter = HomeAskListAdapter(requireContext())

        binding.rvHomeAskList.adapter = askListAdapter
        binding.rvHomeCompleteList.adapter = completeListAdapter

        homeViewModel.getCommunicationList()

        homeViewModel.askList.observe(viewLifecycleOwner){ askList ->
            askListAdapter.replaceAskList(askList)
            binding.txtHomeAskCount.text = "("+askList.size.toString()+")"
        }

        homeViewModel.completeList.observe(viewLifecycleOwner){ completeList ->
            completeListAdapter.replaceAskList(completeList)
            binding.txtHomeCompleteCount.text = "("+completeList.size.toString()+")"
        }
    }

    private fun onClickAskBtn(){
        binding.btnHomeAsk.setOnClickListener {
            val askIntent = Intent(context, AskActivity::class.java)

            startActivity(askIntent)
        }

        binding.btnHomeAskCenter.setOnClickListener {
            val askIntent = Intent(context, AskActivity::class.java)

            startActivity(askIntent)
        }
    }

}