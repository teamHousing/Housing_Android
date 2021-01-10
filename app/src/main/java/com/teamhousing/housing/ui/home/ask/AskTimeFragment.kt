package com.teamhousing.housing.ui.home.ask

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.DialogTimePickerBinding
import com.teamhousing.housing.databinding.FragmentAskTimeBinding
import com.teamhousing.housing.vo.ContactData
import java.util.*

class AskTimeFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskTimeBinding
    private lateinit var adapter : ContactAdapter

    private var contactDate= ""
    private var contactStartTime= ""
    private var contactEndTime= ""
    private var contactMethod = ""

    private lateinit var boldFont: Typeface
    private lateinit var mediumFont: Typeface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        boldFont = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_bold)!!
        mediumFont = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_medium)!!

        changeButtonState()
        makeContact()
        selectDate()
        selectTime(binding.edtTimeStartTime)
        selectTime(binding.edtTimeEndTime)
        addContactList()
    }

    private fun addContactList() {
        binding.btnTimeAdd.setOnClickListener {
            if(!contactMethod.isNullOrBlank() && !contactDate.isNullOrBlank() &&
                    !contactStartTime.isNullOrBlank() && !contactEndTime.isNullOrBlank()){
                viewModel.contactList.value?.add(
                    ContactData(
                        contactDate, contactStartTime + "-" + contactEndTime + "시", contactMethod
                    )
                )
                adapter.notifyDataSetChanged()

                initContactOption()
            }
            else{
                Toast.makeText(requireContext(), "선택 사항을 모두 눌러주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeButtonState() {
        binding.btnTimeMeet.setOnClickListener {
            binding.btnTimeMeet.isChecked = !binding.btnTimeMeet.isChecked
            binding.btnTimeCall.isChecked = false

            if(binding.btnTimeMeet.isChecked){
                contactMethod = "집 방문"
            }else if(!binding.btnTimeCall.isChecked){
                contactMethod = ""
            }
        }
        binding.btnTimeCall.setOnClickListener {
            binding.btnTimeCall.isChecked = !binding.btnTimeCall.isChecked
            binding.btnTimeMeet.isChecked = false

            if(binding.btnTimeCall.isChecked){
                contactMethod = "전화 연락"
            }else if(!binding.btnTimeMeet.isChecked){
                contactMethod = ""
            }
        }
    }

    private fun makeContact() {
        adapter = ContactAdapter()
        adapter.data = viewModel.contactList.value!!

        binding.rvTime.adapter = adapter
        adapter.notifyDataSetChanged()
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
                val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
                    contactDate = changeDateFormat(year,month+1, day)
                    binding.edtTimeDate.setText(contactDate)
                }, year, month, day)
                datePickerDialog.datePicker.minDate = minDate.time.time
                datePickerDialog.show()
                binding.edtTimeDate.clearFocus()
                //if(binding.etTimeDate.text.isNullOrBlank())
            }
        }
    }

    private fun selectTime(edt: EditText) {

        editTextIsChanged(edt)

        val dialogBinding : DialogTimePickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.dialog_time_picker, null, false
        )

        edt.setOnFocusChangeListener { _, chk ->
            if(chk){
                val dialog = Dialog(requireContext())

                if(dialogBinding.root.parent != null){
                    val viewGroup : ViewGroup = dialogBinding.root.parent as ViewGroup
                    viewGroup.removeView(dialogBinding.root)
                }
                dialog.setContentView(dialogBinding.root)

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
                }else{
                    view.typeface = boldFont
                    view.setBackgroundResource(R.drawable.border_black_title_underline)
                }
            }
        })
    }
}