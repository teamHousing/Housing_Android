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
    var isTenantValid : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_select)

        selectOwnerButton()
        selectTenantButton()

    }

    private fun selectOwnerButton(){
        binding.btnUserselectOwner.setOnClickListener {
            binding.btnUserselectOwner.isChecked = !binding.btnUserselectOwner.isChecked
            binding.btnUserselectTenant.isChecked = false
            if(binding.btnUserselectOwner.isChecked){
                binding.btnUserselectNext.isEnabled=false
                binding.btnUserselectNext.setBackgroundResource(R.drawable.border_black_fill_200)
            }
            else {
                binding.btnUserselectNext.isEnabled = false
                binding.btnUserselectNext.setBackgroundResource(R.drawable.border_gray_fill_200)
            }
        }
    }

    private fun selectTenantButton(){
        binding.btnUserselectTenant.setOnClickListener {
            binding.btnUserselectTenant.isChecked = !binding.btnUserselectTenant.isChecked
            binding.btnUserselectOwner.isChecked = false
            //세입자 버튼 선택됐을 때만 다음 버튼 활성화
            if(binding.btnUserselectTenant.isChecked){
                binding.btnUserselectNext.isEnabled = true
                binding.btnUserselectNext.setBackgroundResource(R.drawable.border_black_fill_200)
                //세입자 타입을 intent로 넘겨주기-> 1 : 세입자, Postman통신할 때 :type 빼고 ~~/1 넣어서 보내주면 됨
                //joinActivity로 넘어가기
                isTenantValid = 1
                binding.btnUserselectNext.setOnClickListener{
                    val intent = Intent(this, AuthNumberActivity::class.java)
                    intent.putExtra("type", isTenantValid)
                    startActivity(intent)

                }
            }
            //세입자 버튼 선택 안됐을 때 다음 버튼 비활성화
            else{
                binding.btnUserselectNext.isEnabled = false
                binding.btnUserselectNext.setBackgroundResource(R.drawable.border_gray_fill_200)
            }
        }
    }
}