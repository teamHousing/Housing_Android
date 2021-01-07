package com.teamhousing.housing.util

import android.graphics.Color
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.teamhousing.housing.R

class ChangeButtonAttribute {

    private var buttonCheckList = mutableListOf<Boolean>()

    private lateinit var buttonList : MutableList<Button>
    private lateinit var buttonList2 : MutableList<ConstraintLayout>

    var btnClickStatus = -1

    fun changeButtonState(list: ArrayList<Button>, num : Int, case : Int) { // For button
        buttonList = list
        for (i in 0..num) buttonCheckList.add(false)
        for( i in 0..num){
            buttonList[i].setOnClickListener {
                buttonCheckList[i] = !buttonCheckList[i]
                for(j in 0..num) if(i != j) buttonCheckList[j] = false
                if(case == 0) changeButtonAttribute(num)
                else if(case == 1) changeButtonAttribute2(num)
            }
        }
    }

    fun changeButtonState2(list: ArrayList<ConstraintLayout>, num : Int) { // For ConstraintLayout button
        buttonList2 = list
        for (i in 0..num) buttonCheckList.add(false)
        for( i in 0..num){
            buttonList2[i].setOnClickListener {
                buttonCheckList[i] = !buttonCheckList[i]
                for(j in 0..num) {
                    if(i != j) buttonCheckList[j] = false
                    else btnClickStatus = i
                }
                changeButtonAttribute3(num)
            }
        }
    }

    fun changeButtonState3(list : ArrayList<Button>, editText: EditText, num : Int) {
        buttonList = list
        for (i in 0..num) buttonCheckList.add(false)
        for( i in 0..num){
            buttonList[i].setOnClickListener {
                buttonCheckList[i] = !buttonCheckList[i]
                for(j in 0..num) if(i != j) buttonCheckList[j] = false
                editText.clearFocus()
                changeButtonAttribute2(num)
            }
        }

        editText.setOnFocusChangeListener { view, chk ->
            if(chk) {
                view.setBackgroundResource(R.drawable.border_orange_line_12)
                for(i in 0..num) buttonCheckList[i] = false
                changeButtonAttribute2(num)
            }
            else view.setBackgroundResource(R.drawable.border_gray_line_12)
        }
    }

    private fun changeButtonAttribute(num: Int) {   // For AskContentFragment
        for (i in 0..num) {
            if(buttonCheckList[i]){
                buttonList[i].setBackgroundResource(R.drawable.border_orange_fill_1000)
                buttonList[i].setTextColor(Color.parseColor("#ffffff"))
            }else{
                buttonList[i].setBackgroundResource(R.drawable.border_gray_line_1000)
                buttonList[i].setTextColor(Color.parseColor("#080808"))
            }
        }
    }

    private fun changeButtonAttribute2(num: Int) {  //  Used for AskMemoFragment
        for (i in 0..num) {
            if(buttonCheckList[i]){
                buttonList[i].setBackgroundResource(R.drawable.border_orange_line_12)
            }else{
                buttonList[i].setBackgroundResource(R.drawable.border_gray_line_12)
            }
        }
    }

    private fun changeButtonAttribute3(num: Int) {  //  For constraintLayout button
        for (i in 0..num) {
            if(buttonCheckList[i]){
                buttonList2[i].setBackgroundResource(R.drawable.border_orange_line_16)
            }else{
                buttonList2[i].setBackgroundResource(R.drawable.border_gray_line_16)
            }
        }
    }

    private fun changeButtonAttribute4(num: Int) {  //  For constraintLayout button
        for (i in 0..num) {
            if(buttonCheckList[i]){
                buttonList2[i].setBackgroundResource(R.drawable.border_orange_line_16)
            }else{
                buttonList2[i].setBackgroundResource(R.drawable.border_gray_line_16)
            }
        }
    }
}