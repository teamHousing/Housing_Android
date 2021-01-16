package com.teamhousing.housing.ui.home.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityHomeDetailBinding
import com.teamhousing.housing.ui.home.detail.adapter.ViewPagerAdapter
import com.teamhousing.housing.ui.home.detail.viewModel.HomeDetailViewModel
import com.teamhousing.housing.util.UserTokenManager

class HomeDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeDetailBinding
    private lateinit var viewPagerAdapter : ViewPagerAdapter
    private val homeDetailViewModel : HomeDetailViewModel by viewModels()
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_detail)
        binding.viewModel = homeDetailViewModel
        binding.lifecycleOwner=this

        initViewPager()
        initTab()
        setInfo()
    }

    private  fun initViewPager(){
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            HomeDetailInfoFragment(),
            HomeDetailNoteFragment()
        )

        binding.vpHomeDetail.adapter = viewPagerAdapter
    }

    fun sendData() : Int{
        return id
    }

    private fun initTab(){
        binding.tlHomeDetail.setupWithViewPager(binding.vpHomeDetail)
        binding.tlHomeDetail.apply {
            getTabAt(0)?.text = "상세 정보"
            getTabAt(1)?.text = "하우징 쪽지"
        }
    }

    private fun setInfo(){
        if(intent.hasExtra("id")){
            id = intent.getIntExtra("id", 0)
            homeDetailViewModel.getCommunicationDetail(UserTokenManager.getToken(this), id)
        }
    }
}