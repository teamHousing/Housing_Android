package com.teamhousing.housing.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityLoginBinding
import com.teamhousing.housing.ui.join.UserSelectActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var etLoginId:EditText
    private lateinit var etLoginPassword:EditText
    private lateinit var buttonConfirm:Button

    //서버통신할 때 서버에 등록된 아이디와 비밀번호 체크 후에 로그인 버튼을 활성화시켜야함
    private val loginTextWatcher = object:TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val loginIdInput = etLoginId.getText().toString().trim()
            val loginpwInput = etLoginPassword.getText().toString().trim()
            //로그인 id, password != empty 일 때 버튼 활성화
            buttonConfirm.setEnabled(!loginIdInput.isEmpty() && !loginpwInput.isEmpty())
            //로그인 버튼 색상 변경
            if(!loginIdInput.isNullOrBlank() && !loginpwInput.isNullOrBlank())
                buttonConfirm.setBackgroundResource(R.drawable.border_black_fill_200)
            //서버통신 성공시 화면전환, 실패시 다시 버튼 비활성화
            else
                buttonConfirm.setBackgroundResource(R.drawable.border_gray_fill_200)

            checkBlankAndImgbtn(binding.btnLoginIdclear, loginIdInput, etLoginId)
            checkBlankAndImgbtn(binding.btnLoginPwclear, loginpwInput, etLoginPassword)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        etLoginId = binding.etLoginId
        etLoginPassword = binding.etLoginPw
        buttonConfirm = binding.btnLoginLogin
        etLoginId.addTextChangedListener(loginTextWatcher)
        etLoginPassword.addTextChangedListener(loginTextWatcher)

        //회원가입 클릭 시 사용자 선택 액티비티로 전환
        binding.btnLoginJoin.setOnClickListener{
            val intent = Intent(this, UserSelectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkBlankAndImgbtn(imgV: ImageView, str: String, edt: EditText){
        if(!str.isEmpty()){
            imgV.isVisible = true
            imgV.setOnClickListener{
                edt.getText().clear()
            }
        }
        else if(str.isEmpty())
            imgV.isVisible = false
        else {}
    }
}