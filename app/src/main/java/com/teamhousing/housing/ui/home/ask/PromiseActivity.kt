package com.teamhousing.housing.ui.home.ask

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityPromiseBinding
import com.teamhousing.housing.databinding.DialogTimePickerBinding
import com.teamhousing.housing.vo.ContactData
import java.util.*
import kotlin.collections.ArrayList

class PromiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPromiseBinding
    private lateinit var adapter : ContactAdapter
    private val viewModel : PromiseViewModel by viewModels()
    private val promiseList = mutableListOf<ContactData>()

    private var contactDate= ""
    private var contactStartTime= ""
    private var contactEndTime= ""
    private var contactMethod = ""

    private lateinit var boldFont: Typeface
    private lateinit var mediumFont: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_promise)
        boldFont = ResourcesCompat.getFont(this, R.font.apple_sd_gothic_neo_bold)!!
        mediumFont = ResourcesCompat.getFont(this, R.font.apple_sd_gothic_neo_medium)!!

        changeButtonState()
        makeContact()
        selectDate()
        selectTime(binding.edtTimeStartTime)
        selectTime(binding.edtTimeEndTime)
        addContactList()
        requestAsk()
    }

    private fun addContactList() {
        binding.btnTimeAdd.setOnClickListener {
            promiseList.add(
                ContactData(
                    contactDate, contactStartTime + "-" + contactEndTime + "시", contactMethod
                )
            )
            viewModel.changePromiseList(promiseList as ArrayList<ContactData>)
            initContactOption()
        }


        viewModel.promiseList.observe(this, { list ->
            binding.btnTimeAssign.isEnabled = list.size != 0
            adapter.data = list
            adapter.notifyDataSetChanged()
        })
    }

    private fun changeButtonState() {
        binding.btnTimeMeet.setOnClickListener {
            binding.btnTimeMeet.isChecked = !binding.btnTimeMeet.isChecked
            binding.btnTimeCall.isChecked = false

            if(binding.btnTimeMeet.isChecked){
                contactMethod = "집 방문"
                if(!binding.edtTimeDate.text.isNullOrBlank() && !binding.edtTimeStartTime.text.isNullOrBlank()
                    && !binding.edtTimeEndTime.text.isNullOrBlank()){
                    binding.btnTimeAdd.isEnabled = true
                    binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                }
            }else if(!binding.btnTimeCall.isChecked){
                contactMethod = ""
                binding.btnTimeAdd.isEnabled = false
                binding.btnTimeAdd.setTextColor(Color.parseColor("#cbd6de"))
            }
        }
        binding.btnTimeCall.setOnClickListener {
            binding.btnTimeCall.isChecked = !binding.btnTimeCall.isChecked
            binding.btnTimeMeet.isChecked = false

            if(binding.btnTimeCall.isChecked){
                contactMethod = "전화"
                if(!binding.edtTimeDate.text.isNullOrBlank() && !binding.edtTimeStartTime.text.isNullOrBlank()
                    && !binding.edtTimeEndTime.text.isNullOrBlank()){
                    binding.btnTimeAdd.isEnabled = true
                    binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                }
            }else if(!binding.btnTimeMeet.isChecked){
                contactMethod = ""
                binding.btnTimeAdd.isEnabled = false
                binding.btnTimeAdd.setTextColor(Color.parseColor("#cbd6de"))
            }
        }
    }

    private fun makeContact() {
        adapter = ContactAdapter()
        binding.rvTime.adapter = adapter
    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)

        val minDate = Calendar.getInstance()
        minDate.set(year, month, day)

        editTextIsChanged(binding.edtTimeDate)

        binding.edtTimeDate.setOnFocusChangeListener { _, chk ->
            if(chk){
                val datePickerDialog = DatePickerDialog(this,{ _, year, month, day ->
                    contactDate = changeDateFormat(year,month+1, day)
                    binding.edtTimeDate.setText(contactDate)
                }, year, month, day)
                datePickerDialog.datePicker.minDate = minDate.time.time
                datePickerDialog.show()
                binding.edtTimeDate.clearFocus()
            }
        }
    }

    private fun selectTime(edt: EditText) {

        editTextIsChanged(edt)

        val dialogBinding : DialogTimePickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ), R.layout.dialog_time_picker, null, false
        )

        edt.setOnFocusChangeListener { _, chk ->
            if(chk){
                edt.clearFocus()
                val alertDialog = AlertDialog.Builder(this)
                val dialog: AlertDialog = alertDialog.create()

                if(dialogBinding.root.parent != null){
                    val viewGroup : ViewGroup = dialogBinding.root.parent as ViewGroup
                    viewGroup.removeView(dialogBinding.root)
                }

                dialogBinding.btnDatePickerCancel.setOnClickListener {
                    dialog.dismiss()
                    dialog.cancel()
                    edt.clearFocus()
                }

                dialogBinding.btnDatePickerOk.setOnClickListener {
                    val tempTime = changeHourFormat(
                        dialogBinding.pckHour.value,
                        dialogBinding.pckMeridian.value
                    )
                    edt.setText(tempTime + "시")
                    dialog.dismiss()
                    dialog.cancel()
                    edt.clearFocus()

                    if(edt == binding.edtTimeStartTime){
                        contactStartTime = tempTime
                    }else{
                        contactEndTime = tempTime
                    }
                }
                dialogBinding.pckHour.wrapSelectorWheel = false
                dialogBinding.pckMeridian.wrapSelectorWheel = false

                dialogBinding.pckMeridian.minValue = 0
                dialogBinding.pckMeridian.maxValue = 1
                dialogBinding.pckMeridian.displayedValues = arrayOf("AM", "PM")

                dialogBinding.pckHour.minValue = 1
                dialogBinding.pckHour.maxValue = 12

                dialog.setView(dialogBinding.root)
                dialog.show()
            }
        }
    }

    private fun changeDateFormat(year: Int, month: Int, day: Int): String {
        var m = month.toString()
        var d = day.toString()
        if(month < 10) m = "0$month"
        if(day < 10) d = "0$day"
        return "$year.$m.$d"
    }

    private fun changeHourFormat(hour: Int, meridian: Int): String {
        if(meridian == 1) return (hour + 12).toString()
        if(hour < 10) return "0$hour"
        return hour.toString()
    }

    private fun initContactOption() {
        binding.btnTimeMeet.isChecked = false
        binding.btnTimeCall.isChecked = false
        binding.edtTimeDate.setText("")
        binding.edtTimeStartTime.setText("")
        binding.edtTimeEndTime.setText("")

        contactDate= ""
        contactStartTime= ""
        contactEndTime= ""
        contactMethod = ""
    }

    private fun editTextIsChanged(view : EditText){
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()){
                    view.typeface = mediumFont
                    binding.btnTimeAdd.isEnabled = false
                    binding.btnTimeAdd.setTextColor(Color.parseColor("#cbd6de"))
                }else{
                    view.typeface = boldFont
                    view.setBackgroundResource(R.drawable.border_black_title_underline)
                    if(view == binding.edtTimeDate && !contactMethod.isNullOrBlank()
                        && !binding.edtTimeStartTime.text.isNullOrBlank()
                        && !binding.edtTimeEndTime.text.isNullOrBlank()){
                        binding.btnTimeAdd.isEnabled = true
                        binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                    }
                    else if(view == binding.edtTimeStartTime && !contactMethod.isNullOrBlank()
                        && !binding.edtTimeDate.text.isNullOrBlank()
                        && !binding.edtTimeEndTime.text.isNullOrBlank()){
                        binding.btnTimeAdd.isEnabled = true
                        binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                    }
                    else if(view == binding.edtTimeEndTime && !contactMethod.isNullOrBlank()
                        && !binding.edtTimeDate.text.isNullOrBlank()
                        && !binding.edtTimeStartTime.text.isNullOrBlank()){
                        binding.btnTimeAdd.isEnabled = true
                        binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                    }
                }
            }
        })
    }

    private fun requestAsk() {
        binding.btnTimeAssign.setOnClickListener {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}