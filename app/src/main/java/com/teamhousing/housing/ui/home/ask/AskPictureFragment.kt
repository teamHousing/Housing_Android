package com.teamhousing.housing.ui.home.ask

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.FragmentAskPictureBinding

class AskPictureFragment : Fragment() {

    private val viewModel : AskViewModel by activityViewModels()
    private lateinit var binding: FragmentAskPictureBinding
    private lateinit var filesAdapter : FilesAdapter

    companion object {
        const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE_IMAGE = 1001
        const val CAMERA_CAPTURE_CODE = 1002
        const val PERMISSION_CODE_CAMERA = 1003
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_picture, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPictureNext.setOnClickListener {
            (activity as AskActivity).replaceFragment(AskMemoFragment())
        }

        filesAdapter = FilesAdapter()
        binding.rvPicture.adapter = filesAdapter

        picturesFromCamera()
        picturesFromGallery()
    }

    private fun picturesFromGallery() {
        binding.btnPictureGallery.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED){
                    // permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE_IMAGE)
                }else{
                    selectImageFromGallery()
                }
            }else{
                // OS is < Marshmallow
                selectImageFromGallery()
            }
        }
        binding.btnPictureLoadCameraDelete.setOnClickListener {
            binding.clLoadCamera.visibility = View.INVISIBLE
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        val chooserIntent = Intent.createChooser(intent, "Select Pictures")
        startActivityForResult(chooserIntent, IMAGE_PICK_CODE)
    }

    private fun picturesFromCamera() {
        binding.btnPictureCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    // permission denied
                    val permissions = arrayOf(android.Manifest.permission.CAMERA)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE_CAMERA)
                }else{
                    takeImage()
                }
            }else{
                // OS is < Marshmallow
                takeImage()
            }
        }
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE_IMAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImageFromGallery()
                } else { // permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeImage()
                } else { // permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            when(requestCode){
                CAMERA_CAPTURE_CODE -> {
                    var bmp = data?.extras!!.get("data") as Bitmap
                    binding.ivPictureLoadPicture.setImageBitmap(bmp)
                    binding.clLoadCamera.visibility = View.VISIBLE
                    binding.rvPicture.visibility = View.INVISIBLE
                    viewModel.changeFileBitmap(bmp)
                }
                IMAGE_PICK_CODE -> {
                    filesAdapter.uriData.clear()
                    if (data?.clipData != null) {
                        val filesSize = data.clipData!!.itemCount
                        var uriList: MutableList<Uri> = mutableListOf()

                        if (filesSize > 5) {
                            Toast.makeText(
                                requireContext(),
                                "이미지 첨부는 5장까지만 가능합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            for (i in 0 until 5) {
                                var dataUri = data.clipData!!.getItemAt(i).uri
                                filesAdapter.uriData.add(dataUri)
                                uriList.add(absolutelyPath(dataUri).toUri())
                            }
                            viewModel.changeFilesUri(uriList as ArrayList<Uri>)
                        } else {
                            for (i in 0 until filesSize) {
                                var dataUri = data.clipData!!.getItemAt(i).uri
                                filesAdapter.uriData.add(dataUri)
                                uriList.add(absolutelyPath(dataUri).toUri())
                            }
                            viewModel.changeFilesUri(uriList as ArrayList<Uri>)
                        }
                        binding.rvPicture.visibility = View.VISIBLE
                        binding.clLoadCamera.visibility = View.INVISIBLE
                    }
                }
            }
            filesAdapter.notifyDataSetChanged()
        }
    }

    private fun absolutelyPath(path: Uri): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c= context?.contentResolver?.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }
}


