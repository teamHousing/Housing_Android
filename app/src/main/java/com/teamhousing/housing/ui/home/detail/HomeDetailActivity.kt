package com.teamhousing.housing.ui.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityHomeDetailBinding
import com.teamhousing.housing.ui.home.detail.adapter.ViewPagerAdapter

class HomeDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeDetailBinding
    private lateinit var viewPagerAdapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_detail)
        binding.lifecycleOwner=this

        initViewPager()
        initTab()
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

    private fun initTab(){
        binding.tlHomeDetail.setupWithViewPager(binding.vpHomeDetail)
        binding.tlHomeDetail.apply {
            getTabAt(0)?.text = "상세 정보"
            getTabAt(1)?.text = "하우징 쪽지"
        }
    }
}