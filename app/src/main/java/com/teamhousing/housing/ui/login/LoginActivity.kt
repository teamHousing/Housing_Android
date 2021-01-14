package com.teamhousing.housing.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityLoginBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.ui.home.HomeFragment
import com.teamhousing.housing.ui.join.UserSelectActivity
import com.teamhousing.housing.ui.main.MainActivity
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.RequestLoginData
import com.teamhousing.housing.vo.ResponseLoginData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    //로그인 실패 시
    private fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        binding.tvLoginWrong.isVisible = true
        binding.etLoginId.setBackgroundResource(R.drawable.border_red_underline)
        binding.etLoginPw.setBackgroundResource(R.drawable.border_red_underline)
        binding.btnLoginLogin.setBackgroundResource(R.drawable.border_gray_fill_200)
        binding.btnLoginLogin.isEnabled = false
        //Toast.makeText(this, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.etLoginId.addTextChangedListener(loginTextWatcher)
        binding.etLoginPw.addTextChangedListener(loginTextWatcher)

        //회원가입 클릭 시 사용자 선택 액티비티로 전환
        binding.btnLoginJoin.setOnClickListener {
            val intent = Intent(this, UserSelectActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginLogin.setOnClickListener{
            val email = binding.etLoginId.text.toString()
            val password = binding.etLoginPw.text.toString()
            val call : Call<ResponseLoginData> = HousingServiceImpl.service.postLogin(
                    RequestLoginData(email = email, password = password)
            )
            call.enqueue(object : Callback<ResponseLoginData>{
                override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                    Log.e("error", t.toString())
                }
                override fun onResponse(
                        call: Call<ResponseLoginData>,
                        response: Response<ResponseLoginData>
                ) {
                    response.takeIf { it.isSuccessful}
                            ?.body()
                            ?.let {
                                binding.etLoginId.setBackgroundResource(R.drawable.border_black_underline)
                                binding.etLoginPw.setBackgroundResource(R.drawable.border_black_underline)
                                binding.tvLoginWrong.isVisible = false

                                UserTokenManager.init(this@LoginActivity)
                                UserTokenManager.token = response.body()!!.data.user_token
                                //Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            } ?: showError(response.errorBody())
                }
            }
            )
        }
    }

    //서버통신할 때 서버에 등록된 아이디와 비밀번호 체크 후에 로그인 버튼을 활성화시켜야함
    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val loginIdInput = binding.etLoginId.getText().toString().trim()
            val loginpwInput = binding.etLoginPw.getText().toString().trim()
            //로그인 id, password != empty 일 때 버튼 활성화
            binding.btnLoginLogin.setEnabled(!loginIdInput.isEmpty() && !loginpwInput.isEmpty())
            //로그인 버튼 색상 변경
            if (!loginIdInput.isNullOrBlank() && !loginpwInput.isNullOrBlank())
                binding.btnLoginLogin.setBackgroundResource(R.drawable.border_black_fill_200)
            //서버통신 성공시 화면전환, 실패시 다시 버튼 비활성화
            else
                binding.btnLoginLogin.setBackgroundResource(R.drawable.border_gray_fill_200)

            checkBlankAndImgbtn(binding.btnLoginIdclear, loginIdInput, binding.etLoginId)
            checkBlankAndImgbtn(binding.btnLoginPwclear, loginpwInput, binding.etLoginPw)
        }
    }

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
}