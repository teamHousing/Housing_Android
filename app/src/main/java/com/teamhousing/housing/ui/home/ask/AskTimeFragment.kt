package com.teamhousing.housing.ui.home.ask

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.DialogTimePickerBinding
import com.teamhousing.housing.databinding.FragmentAskTimeBinding
import java.util.*

class AskTimeFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskTimeBinding
    private lateinit var adapter : ContactAdapter

    private lateinit var contactDate: String
    private lateinit var contactStartTime: String
    private lateinit var contactEndTime: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButtonState()
        makeContact()
        selectDate()
        selectTime(binding.edtTimeStartTime)
        selectTime(binding.edtTimeEndTime)
        addContactList()

    }

    private fun addContactList() {

    }

    private fun changeButtonState() {
        binding.btnTimeMeet.setOnClickListener {
            binding.btnTimeMeet.isChecked = !binding.btnTimeMeet.isChecked
            binding.btnTimeCall.isChecked = false
        }
        binding.btnTimeCall.setOnClickListener {
            binding.btnTimeCall.isChecked = !binding.btnTimeCall.isChecked
            binding.btnTimeMeet.isChecked = false
        }
    }

    private fun makeContact() {
        adapter = ContactAdapter()
        adapter.data = viewModel.contactList

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
}