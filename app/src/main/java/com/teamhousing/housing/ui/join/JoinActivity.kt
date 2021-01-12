package com.teamhousing.housing.ui.join

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityJoinBinding
import com.teamhousing.housing.databinding.ActivityLoginBinding
import com.teamhousing.housing.ui.login.LoginActivity

class JoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding
    private lateinit var etJoinName: EditText
    private lateinit var etJoinAge: EditText
    private lateinit var etJoinId: EditText
    private lateinit var etJoinPassword: EditText
    private lateinit var etJoinPasswordCheck: EditText
    private lateinit var buttonConfirm: Button
    private lateinit var tvjoinPasswordCheck: TextView
    var isValid = false
    var isValidPw = false

    private val JoinTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val joinNameInput = etJoinName.getText().toString().trim()
            val joinAgeInput = etJoinAge.getText().toString().trim()
            val joinIdInput = etJoinId.getText().toString().trim()
            val joinPwInput = etJoinPassword.getText().toString().trim()
            val joinPwcInput = etJoinPasswordCheck.getText().toString().trim()

            // 회원가입 뷰 모든 입력칸 공백아니어야 함
            buttonConfirm.setEnabled(!joinNameInput.isEmpty() && !joinAgeInput.isEmpty() && !joinIdInput.isEmpty()
                    && !joinPwInput.isEmpty() && !joinPwcInput.isEmpty() && joinPwInput.equals(joinPwcInput))

            //완료 버튼 색상 변경
            if (!joinNameInput.isNullOrBlank() && !joinAgeInput.isNullOrBlank() && !joinIdInput.isNullOrBlank()
                    && !joinPwInput.isNullOrBlank() && !joinPwcInput.isNullOrBlank() && joinPwInput.equals(joinPwcInput)) {
                buttonConfirm.setBackgroundResource(R.drawable.border_black_fill_200)
                tvjoinPasswordCheck.isVisible = false
                etJoinPasswordCheck.setBackgroundResource(R.drawable.border_black_underline)
                //완료 누르면 로그인 화면으로 이동
                goToLogin(buttonConfirm)
            } else if (!joinPwInput.equals(joinPwcInput)) {
                buttonConfirm.setBackgroundResource(R.drawable.border_gray_fill_200)
                etJoinPasswordCheck.setBackgroundResource(R.drawable.border_red_underline)
                tvjoinPasswordCheck.isVisible = true
            } else {
                buttonConfirm.setBackgroundResource(R.drawable.border_gray_fill_200)
                tvjoinPasswordCheck.isVisible = false
                etJoinPasswordCheck.setBackgroundResource(R.drawable.border_black_underline)
            }

            checkBlankAndImgbtn(binding.btnJoinNameclear, joinNameInput, etJoinName)
            checkBlankAndImgbtn(binding.btnJoinAgeclear, joinAgeInput, etJoinAge)
            checkBlankAndImgbtn(binding.btnJoinIdclear, joinIdInput, etJoinId)
            checkBlankAndImgbtn(binding.btnJoinPwclear, joinPwInput, etJoinPassword)
            checkBlankAndImgbtn(binding.btnJoinPwcClear, joinPwcInput, etJoinPasswordCheck)

            checkPasswordValid(joinPwcInput, binding.btnJoinPwcClear, etJoinPasswordCheck)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        etJoinName = binding.etJoinName
        etJoinAge = binding.etJoinAge
        etJoinId = binding.etJoinId
        etJoinPassword = binding.etJoinPw
        etJoinPasswordCheck = binding.etJoinPwC
        buttonConfirm = binding.btnJoinComplete
        tvjoinPasswordCheck = binding.tvJoinPwc

        etAddTextChangedListener()

        binding.btnJoinBack.setOnClickListener{
            val intent = Intent(this, AuthNumberActivity::class.java)
            startActivity(intent)
        }
    }
    private fun etAddTextChangedListener(){
        etJoinName.addTextChangedListener(JoinTextWatcher)
        etJoinAge.addTextChangedListener(JoinTextWatcher)
        etJoinId.addTextChangedListener(JoinTextWatcher)
        etJoinPassword.addTextChangedListener(JoinTextWatcher)
        etJoinPasswordCheck.addTextChangedListener(JoinTextWatcher)
        tvjoinPasswordCheck.addTextChangedListener(JoinTextWatcher)
    }

    //이미지버튼, edittext공백 체크
    private fun checkBlankAndImgbtn(imgV: ImageView, str: String, edt: EditText) {
        if (!str.isEmpty()) {
            imgV.isVisible = true
            imgV.setOnClickListener {
                edt.getText().clear()
            }
        } else if (str.isEmpty())
            imgV.isVisible = false
        else {
        }
    }

    //비밀번호 재확인
    private fun checkPasswordValid(str: String, imgV: ImageView, edt: EditText) {
        if (!str.isEmpty()) {
            imgV.isVisible = true
            imgV.setOnClickListener {
                edt.getText().clear()
            }
        } else if (str.isEmpty())
            imgV.isVisible = false
        else {
        }
    }

    fun goToLogin(btn: Button) {
        btn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}