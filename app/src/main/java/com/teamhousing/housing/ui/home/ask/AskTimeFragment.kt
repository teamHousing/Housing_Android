package com.teamhousing.housing.ui.home.ask

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskTimeBinding
import com.teamhousing.housing.util.ChangeButtonAttribute
import java.util.*
import kotlin.collections.ArrayList

class AskTimeFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskTimeBinding
    var buttonList = mutableListOf<ConstraintLayout>()

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
    }

    private fun changeButtonState() {
        buttonList = arrayListOf(binding.btnTimeMeet, binding.btnTimeCall)
        val buttonListener = ChangeButtonAttribute()
        buttonListener.changeButtonState2(buttonList as ArrayList<ConstraintLayout>, 1)
    }

    private fun makeContact() {
        var adapter = ContactAdapter()
        adapter.data = viewModel.contactList

        binding.rvTime.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)

        binding.edtTimeDate.setOnFocusChangeListener { _, chk ->
            if(chk){
                val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
                    binding.edtTimeDate.setText(year.toString() + "." + month.plus(1).toString() + "." + day.toString())
                }, year, month, day)
                datePickerDialog.show()
                binding.edtTimeDate.clearFocus()
                //if(binding.etTimeDate.text.isNullOrBlank())
            }
        }
    }
}