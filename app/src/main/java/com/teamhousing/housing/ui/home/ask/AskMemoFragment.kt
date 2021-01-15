package com.teamhousing.housing.ui.home.ask

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskMemoBinding
import com.teamhousing.housing.network.HousingServiceImpl
import com.teamhousing.housing.util.UserTokenManager
import com.teamhousing.housing.vo.RequestAskData
import com.teamhousing.housing.vo.ResponseAskData
import com.teamhousing.housing.vo.ResponseAskFileData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class AskMemoFragment() : Fragment() {

    companion object {
        const val REQUEST_PROMISE = 201;
    }

    private val viewModel: AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskMemoBinding
    private var buttonList = mutableListOf<CheckedTextView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_memo, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButtonState()
        editTextIsChanged(binding.edtMemoDirect)

        nextPage()
    }

    private fun changeButtonState() {
        when (viewModel.isPromise.value) {
            0 -> binding.btnMemoNext.text = "다음 단계"
            1 -> binding.btnMemoNext.text = "등록하기"
        }

        buttonList = arrayListOf(
            binding.btnMemoThank, binding.btnMemoQuick,
            binding.btnMemoPrecontact, binding.btnMemoAbsence
        )

        for (i in 0..3) {
            buttonList[i].setOnClickListener {
                for (j in 0..3) if (i != j) {
                    buttonList[j].isChecked = false
                    viewModel.changeRequestedTerm("")
                }
                binding.edtMemoDirect.clearFocus()
                buttonList[i].isChecked = !buttonList[i].isChecked
                if (buttonList[i].isChecked) {
                    viewModel.changeRequestedTerm(buttonList[i].text.toString())
                    binding.btnMemoNext.isEnabled = true
                } else {
                    binding.btnMemoNext.isEnabled = false
                }
            }
        }
        binding.edtMemoDirect.setOnFocusChangeListener { _, chk ->
            if (chk) for (i in 0..3) {
                buttonList[i].isChecked = false
                binding.btnMemoNext.isEnabled = !binding.edtMemoDirect.text.isNullOrBlank()
            }
            else binding.btnMemoNext.isEnabled = false
        }
    }

    private fun editTextIsChanged(view: EditText) {
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeRequestedTerm(s.toString())
                binding.btnMemoNext.isEnabled = !s.isNullOrBlank()
            }
        })
    }

    private fun nextPage() {
        val token = UserTokenManager.getToken(requireContext())

        binding.btnMemoNext.setOnClickListener {
            var imageParts = mutableListOf<MultipartBody.Part>()
            var filesCall: Call<ResponseAskFileData>

            if(!viewModel.issueFilesUri.value.isNullOrEmpty()){
                for (i in 0 until viewModel.issueFilesUri.value!!.size) {
                    val file = File(viewModel.issueFilesUri.value!![i].toString())
                    var requestBody : RequestBody = RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                    )
                    imageParts.add(
                        MultipartBody.Part.createFormData(
                            "issue_img",
                            file.name,
                            requestBody
                        )
                    )
                }
                filesCall = HousingServiceImpl.service.postCommunicationFiles(token, imageParts)
            }else{
                filesCall = HousingServiceImpl.service.postCommunicationNoFiles(token)
            }
            filesCall.enqueue(object : Callback<ResponseAskFileData> {
                override fun onResponse(
                    call: Call<ResponseAskFileData>,
                    response: Response<ResponseAskFileData>
                ) {
                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                            Log.e("asd", it.data.issue_id.toString())
                            requestContent(token, it.data.issue_id)
                        } ?: showError(response.errorBody())
                }

                override fun onFailure(call: Call<ResponseAskFileData>, t: Throwable) {
                }
            })
            var list = arrayListOf<Uri>()
            viewModel.changeFilesUri(list)
        }
    }

    fun requestContent(token: String, askId: Int){
        val contentCall: Call<ResponseAskData> =
            HousingServiceImpl.service.postCommunication(
                token,
                askId,
                RequestAskData(
                    viewModel.category.value!!, false,
                    viewModel.issueContents.value!!, viewModel.issueTitle.value!!,
                    viewModel.requestedTerm.value!!
                )
            )
        contentCall.enqueue(object : Callback<ResponseAskData> {
            override fun onResponse(
                call: Call<ResponseAskData>,
                response: Response<ResponseAskData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let {
                        when (viewModel.isPromise.value) {
                            0 -> {  // 약속이 있는 경우
                                val intent = Intent(requireContext(), PromiseActivity::class.java)
                                intent.putExtra("token", token)
                                intent.putExtra("askId", askId)
                                intent.putExtra("isCheckFrom", false)
                                startActivityForResult(intent, REQUEST_PROMISE)
                            }
                            1 -> { // 약속이 없는 경우
                                activity?.finish()
                            }
                            else -> null
                        }
                    }
            }
            override fun onFailure(call: Call<ResponseAskData>, t: Throwable) {
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_PROMISE -> {
                    activity?.finish()
                }
            }
        }
    }

    fun showError(error: ResponseBody?){
        val e = error ?: return
        val ob = (e.string())
        Log.e("asd", ob)
    }
}