package com.teamhousing.housing.ui.join

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityAuthNumberBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.ui.main.MainActivity
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.RequestAuthNumData
import com.teamhousing.housing.vo.ResponseAuthNumData
import com.teamhousing.housing.vo.ResponseLoginData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthNumberBinding
    var isTenantValid : Int = 1
    
    //인증번호 서버통신 실패 시
    fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = (e.string())
        binding.tvAuthnumberValid.isVisible = true
        binding.etAuthnumberAuth.setBackgroundResource(R.drawable.border_red_underline)
        binding.btnAuthnumberNext.setBackgroundResource(R.drawable.border_gray_fill_200)
        binding.btnAuthnumberNext.isEnabled = false
        Log.e("asd", ob)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_number)

        isTenantValid = intent.getIntExtra("type",1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_number)
        binding.etAuthnumberAuth.addTextChangedListener(AuthNumberTextWatcher)

        binding.btnAuthnumberNext.setOnClickListener{
            val authNumber = binding.etAuthnumberAuth.text.toString().toInt()
            val call : Call<ResponseAuthNumData> = HousingServiceImpl.service.postAuthCheck(
                    RequestAuthNumData(authentication_number = authNumber)
            )
            call.enqueue(object : Callback<ResponseAuthNumData> {
                override fun onFailure(call: Call<ResponseAuthNumData>, t: Throwable) {
                    Log.e("error", t.toString())
                }
                override fun onResponse(
                        call: Call<ResponseAuthNumData>,
                        response: Response<ResponseAuthNumData>
                ) {
                    response.takeIf { it.isSuccessful}
                            ?.body()
                            ?.let {
                                Log.e("AuthNumberActivity", it.toString())
                                binding.etAuthnumberAuth.setBackgroundResource(R.drawable.border_black_underline)
                                binding.tvAuthnumberValid.isVisible = false

                                val intent = Intent(this@AuthNumberActivity,JoinActivity::class.java)
                                isTenantValid = 1
                                //받아와짐 얘를 계속 회원가입까지 끌고 나가야 한다
                                intent.putExtra("type", isTenantValid)
                                //입력받은 인증번호 서버 통신 성공 시 intent로 가져가기
                                intent.putExtra("auth_num", binding.etAuthnumberAuth.text.toString().toInt())
                                startActivity(intent)

                            } ?: showError(response.errorBody())
                }
            }
            )
        }

    }

    private val AuthNumberTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //인증번호
            val AuthNumberInput = binding.etAuthnumberAuth.text.toString().toInt()
            binding.btnAuthnumberNext.setEnabled(!binding.etAuthnumberAuth.toString().isEmpty())
            //로그인 버튼 색상 변경
            if (!AuthNumberInput.toString().isEmpty())
                binding.btnAuthnumberNext.setBackgroundResource(R.drawable.border_orange_fill_200)
            else
                binding.btnAuthnumberNext.setBackgroundResource(R.drawable.border_gray_fill_200)

            //content clear 버튼 : 인증번호 != NULL일 때 생성
           checkBlankAndImgbtn(binding.btnAuthnumberClose, AuthNumberInput, binding.etAuthnumberAuth)
        }
    }

    private fun checkBlankAndImgbtn(imgV: ImageView, int: Int , edt: EditText) {
        if (int.toByte() >0) {
            imgV.isVisible = true
            imgV.setOnClickListener {
                edt.getText().clear()
            }
        } else {
            imgV.isVisible = false
        }
    }
}