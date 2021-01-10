package com.teamhousing.housing.ui.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityLoginBinding
import com.teamhousing.housing.databinding.ActivityUserSelectBinding
import com.teamhousing.housing.ui.login.LoginActivity
import kotlinx.coroutines.selects.select

class UserSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_select)

        //임대인 버튼 클릭->구현X
        //세입자 버튼 클릭 시 -> 다음 버튼 활성화
        binding.btnUserselectOwner.setOnClickListener {
            binding.btnUserselectOwner.isChecked = !binding.btnUserselectOwner.isChecked
            binding.btnUserselectTenant.isChecked = false
        }
        binding.btnUserselectTenant.setOnClickListener {
            binding.btnUserselectTenant.isChecked = !binding.btnUserselectTenant.isChecked
            binding.btnUserselectOwner.isChecked = false
            //세입자 버튼 선택됐을 때만 다음 버튼 활성화
            if(binding.btnUserselectTenant.isChecked){
                binding.btnUserselectNext.isEnabled = true
                binding.btnUserselectNext.setBackgroundResource(R.drawable.border_black_fill_200)
                //joinActivity로 넘어가기
                binding.btnUserselectNext.setOnClickListener{
                    val intent = Intent(this, AuthNumberActivity::class.java)
                    startActivity(intent)
                }
            }
            //세입자 버튼 선택 안됐을 때 다음 버튼 비활성화
            else{
                binding.btnUserselectNext.isEnabled = false
                binding.btnUserselectNext.setBackgroundResource(R.drawable.border_gray_fill_200)
            }
        }
        binding.btnUserselectBack.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}