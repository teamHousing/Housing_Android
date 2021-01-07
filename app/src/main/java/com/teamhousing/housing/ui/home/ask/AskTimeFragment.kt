package com.teamhousing.housing.ui.home.ask

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.DialogTimePickerBinding
import com.teamhousing.housing.databinding.FragmentAskTimeBinding
import com.teamhousing.housing.util.ChangeButtonAttribute
import com.teamhousing.housing.vo.ContactData
import java.util.*

class AskTimeFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskTimeBinding
    var buttonList = mutableListOf<ConstraintLayout>()
    private lateinit var adapter : ContactAdapter
    private lateinit var buttonListener : ChangeButtonAttribute

    private lateinit var contactDate: String
    private lateinit var contactStartTime: String
    private lateinit var contactEndTime: String
    private var contactMethod: String = ""

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
        binding.btnTimeAdd.setOnClickListener {
            contactMethod = when(buttonListener.btnClickStatus){
                0 -> "집 방문"
                1 -> "전화 연락"
                else -> ""
            }
            Log.e("asd", ContactData(contactDate, contactStartTime+"-"+contactEndTime+"시", contactMethod).toString())
            viewModel.contactList.add(ContactData(contactDate, contactStartTime+"-"+contactEndTime+"시", contactMethod))
            adapter.notifyDataSetChanged()

            binding.btnTimeMeet.setBackgroundResource(R.drawable.border_gray_line_16)
            binding.btnTimeCall.setBackgroundResource(R.drawable.border_gray_line_16)
            binding.edtTimeDate.setText("")

            buttonListener.btnClickStatus = -1
            contactDate = ""
            contactStartTime = ""
            contactEndTime = ""
            contactMethod = ""
            binding.edtTimeStartTime.setText("")
            binding.edtTimeEndTime.setText("")
        }
    }

    private fun changeButtonState() {
        buttonList = arrayListOf(binding.btnTimeMeet, binding.btnTimeCall)
        buttonListener = ChangeButtonAttribute()
        buttonListener.changeButtonState2(buttonList as ArrayList<ConstraintLayout>, 1)
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