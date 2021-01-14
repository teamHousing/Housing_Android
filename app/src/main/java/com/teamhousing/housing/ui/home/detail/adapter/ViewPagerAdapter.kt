package com.teamhousing.housing.ui.home.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter (fm : FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragments = listOf<Fragment>()

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

}