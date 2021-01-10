package com.teamhousing.housing.ui.join

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityAuthNumberBinding
import com.teamhousing.housing.databinding.ActivityLoginBinding

class AuthNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthNumberBinding
    private lateinit var etAuthNumber: EditText
    private lateinit var buttonConfirm: Button
    var isValidAuth = false

    //테스트 위한 임의 인증번호
    var AuthNum: Int = 1234

    private val AuthNumberTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //인증번호
            val AuthNumberInput = etAuthNumber.getText().toString().trim()

            //사용자가 텍스트 입력하면 clear버튼은 생김 -> 확인 버튼 새롭게 추가
            //확인 버튼 클릭 조건 : 사용자 입력 글자 수 == 4
            //확인 버튼 클릭하면 서버 통신되고, 성공하면 인증번호 일치 문구 뜨면서 다음 버튼 활성화
            //통신 실패하면 인증번호 실패 문구 뜨면서 다음버튼 비활성화 유지지
            //단 인증번호 테스트용 인증번호 임의로 변수 만들어서 버튼 활성화 시켜놓음

            buttonConfirm.setEnabled(AuthNumberInput.equals(AuthNum.toString()))
            //로그인 버튼 색상 변경
            if (AuthNumberInput.equals(AuthNum.toString())) {
                buttonConfirm.setBackgroundResource(R.drawable.border_black_fill_200)
                isValidAuth = true
                goToJoin(isValidAuth, binding.btnAuthnumberNext)
                //여기서 일치검사하고, 다음버튼 화면전환
            }
            /*
            여기서 if문, 서버통신해서 인증번호 일치 검사하고, isAuthValid=1로 바꿔줌 전역변수로 주고
            일치하면 다음버튼 활성화(main에서해야함)
             */
            else
                buttonConfirm.setBackgroundResource(R.drawable.border_gray_fill_200)

            //content clear 버튼 : 인증번호 != NULL일 때 생성
            if (!AuthNumberInput.isEmpty()) {
                binding.btnAuthnumberClose.isVisible = true
                //클릭 시 해당 내용 삭제
                binding.btnAuthnumberClose.setOnClickListener {
                    etAuthNumber.getText().clear()
                }
            }
            //인증번호 입력 칸 공백일 경우 content clear 버튼 invisible
            else if (AuthNumberInput.isEmpty())
                binding.btnAuthnumberClose.isVisible = false
            else {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_number)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_number)

        etAuthNumber = binding.etAuthnumberAuth
        buttonConfirm = binding.btnAuthnumberNext
        etAuthNumber.addTextChangedListener(AuthNumberTextWatcher)

        binding.btnAuthnumberBack.setOnClickListener{
            val intent = Intent(this, UserSelectActivity::class.java)
            startActivity(intent)
        }
    }

    fun goToJoin(isValid: Boolean, btn: Button) {
        if (isValid) {
            btn.setOnClickListener {
                val intent = Intent(this, JoinActivity::class.java)
                startActivity(intent)
            }
        }
    }
}