package com.teamhousing.housing.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initNavigation()
    }

    private fun initNavigation(){
        val navController = Navigation.findNavController(this,
                R.id.nav_host_fragment
        )
        binding.bottomNavMenu.setupWithNavController(navController)
        binding.bottomNavMenu.itemIconTintList = null
    }
}