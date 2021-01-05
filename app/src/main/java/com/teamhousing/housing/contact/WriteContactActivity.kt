package com.teamhousing.housing.contact

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityWriteContactBinding

class WriteContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = DataBindingUtil.setContentView<ActivityWriteContactBinding>(this, R.layout.activity_write_contact)

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fm_temp, WriteContentFragment()).commit()
    }

    fun replaceFragment(fragment : Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.fm_temp, fragment).commit()
    }

    fun finishFragment(fragment : Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.remove(fragment).commit()
        supportFragmentManager.popBackStack()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {    // editText focus 없어지면 키보드 숨기기
        if (currentFocus != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}