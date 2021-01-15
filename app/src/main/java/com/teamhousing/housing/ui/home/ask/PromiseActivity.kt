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
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ActivityPromiseBinding
import com.teamhousing.housing.databinding.DialogTimePickerBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.vo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class PromiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPromiseBinding
    private lateinit var adapter : PromiseAdapter
    private val viewModel : PromiseViewModel by viewModels()
    private val promiseList = mutableListOf<PromiseData>() // 뷰모델에 넣을 리스트

    private var promiseDate= ""
    private var promiseStartTime= ""
    private var promiseEndTime= ""
    private var promiseMethod = ""

    private lateinit var boldFont: Typeface
    private lateinit var mediumFont: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_promise)
        boldFont = ResourcesCompat.getFont(this, R.font.apple_sd_gothic_neo_bold)!!
        mediumFont = ResourcesCompat.getFont(this, R.font.apple_sd_gothic_neo_medium)!!

        changeButtonState()
        makePromiseRV()
        selectDate()
        selectTime(binding.edtTimeStartTime)
        selectTime(binding.edtTimeEndTime)
        addPromiseList()
        submitAsk()
    }

    private fun addPromiseList() {
        binding.btnTimeAdd.setOnClickListener {
            promiseList.add(
                PromiseData(
                    promiseDate, promiseStartTime + "-" + promiseEndTime + "시", promiseMethod
                )
            )
            adapter.data2.add(
                PromiseData(
                    promiseDate, "$promiseStartTime:00-$promiseEndTime:00", promiseMethod
                )
            )
            viewModel.changePromiseList(promiseList as ArrayList<PromiseData>)
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
                promiseMethod = "집 방문"
                if(!binding.edtTimeDate.text.isNullOrBlank() && !binding.edtTimeStartTime.text.isNullOrBlank()
                    && !binding.edtTimeEndTime.text.isNullOrBlank()){
                    binding.btnTimeAdd.isEnabled = true
                    binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                }
            }else if(!binding.btnTimeCall.isChecked){
                promiseMethod = ""
                binding.btnTimeAdd.isEnabled = false
                binding.btnTimeAdd.setTextColor(Color.parseColor("#cbd6de"))
            }
        }
        binding.btnTimeCall.setOnClickListener {
            binding.btnTimeCall.isChecked = !binding.btnTimeCall.isChecked
            binding.btnTimeMeet.isChecked = false

            if(binding.btnTimeCall.isChecked){
                promiseMethod = "전화"
                if(!binding.edtTimeDate.text.isNullOrBlank() && !binding.edtTimeStartTime.text.isNullOrBlank()
                    && !binding.edtTimeEndTime.text.isNullOrBlank()){
                    binding.btnTimeAdd.isEnabled = true
                    binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                }
            }else if(!binding.btnTimeMeet.isChecked){
                promiseMethod = ""
                binding.btnTimeAdd.isEnabled = false
                binding.btnTimeAdd.setTextColor(Color.parseColor("#cbd6de"))
            }
        }
    }

    private fun makePromiseRV() {
        adapter = PromiseAdapter()
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
                    promiseDate = changeDateFormat(year,month+1, day)
                    binding.edtTimeDate.setText(promiseDate)
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
                        promiseStartTime = tempTime
                    }else{
                        promiseEndTime = tempTime
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

        promiseDate= ""
        promiseStartTime= ""
        promiseEndTime= ""
        promiseMethod = ""
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
                    if(view == binding.edtTimeDate && !promiseMethod.isNullOrBlank()
                        && !binding.edtTimeStartTime.text.isNullOrBlank()
                        && !binding.edtTimeEndTime.text.isNullOrBlank()){
                        binding.btnTimeAdd.isEnabled = true
                        binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                    }
                    else if(view == binding.edtTimeStartTime && !promiseMethod.isNullOrBlank()
                        && !binding.edtTimeDate.text.isNullOrBlank()
                        && !binding.edtTimeEndTime.text.isNullOrBlank()){
                        binding.btnTimeAdd.isEnabled = true
                        binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                    }
                    else if(view == binding.edtTimeEndTime && !promiseMethod.isNullOrBlank()
                        && !binding.edtTimeDate.text.isNullOrBlank()
                        && !binding.edtTimeStartTime.text.isNullOrBlank()){
                        binding.btnTimeAdd.isEnabled = true
                        binding.btnTimeAdd.setTextColor(Color.parseColor("#080808"))
                    }
                }
            }
        })
    }

    private fun submitAsk() {
        binding.btnTimeAssign.setOnClickListener {
            var promiseList = mutableListOf<String>()
            var promiseLists = mutableListOf<List<String>>()
            for(i in 0 until adapter.data2.size){
                promiseList.add(adapter.data2[i].date)
                promiseList.add(adapter.data2[i].time)
                promiseList.add(adapter.data2[i].method)
                promiseLists.add(promiseList.toList())
                promiseList.clear()
                }
            Log.e("asd",intent.getIntExtra("askId", 0).toString())
            if(intent.getBooleanExtra("isCheckFrom",true)){
                val promiseCall: Call<ResponsePromiseData> = HousingServiceImpl.service.putPromises(
                    intent.getStringExtra("token")!!,
                    intent.getIntExtra("askId", 0),
                    RequestPromiseData(promiseLists)
                )
                promiseCall.enqueue(object : Callback<ResponsePromiseData> {
                    override fun onResponse(
                        call: Call<ResponsePromiseData>,
                        response: Response<ResponsePromiseData>
                    ) {
                        response.takeIf {it.isSuccessful}
                            ?.body()
                            ?.let{
                                finish()
                            }
                    }
                    override fun onFailure(call: Call<ResponsePromiseData>, t: Throwable) {
                    }
                })

            }
            else{
                val promiseCall: Call<ResponsePromiseData> = HousingServiceImpl.service.postPromises(
                    intent.getStringExtra("token")!!,
                    intent.getIntExtra("askId", 0),
                    RequestPromiseData(promiseLists)
                )
                promiseCall.enqueue(object : Callback<ResponsePromiseData> {
                    override fun onResponse(
                        call: Call<ResponsePromiseData>,
                        response: Response<ResponsePromiseData>
                    ) {
                        response.takeIf {it.isSuccessful}
                            ?.body()
                            ?.let{
                                val intent = Intent()
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                    }
                    override fun onFailure(call: Call<ResponsePromiseData>, t: Throwable) {
                    }
                })
            }
        }
    }
}