package com.example.trackinggoals.screens


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.trackinggoals.databinding.BottomSheetPhotoFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PhotoBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetPhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewBottomSheetPhotoLoad.setOnClickListener {
            Toast.makeText(requireContext(), "load", Toast.LENGTH_SHORT)
            loadImage()
            dismiss()
        }
        binding.textViewBottomSheetPhotoCreate.setOnClickListener {
            Toast.makeText(requireContext(), "photo", Toast.LENGTH_SHORT)
                .show()
        }
        binding.textViewSheetPhotoCancel.setOnClickListener {
            dismiss()
        }
    }

    fun loadImage() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                val Intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(Intent, 2)

            }
        }
    }



//    override fun onDismiss(dialog: DialogInterface) {
//        super.onDismiss(dialog)
//        Toast.makeText(requireContext(), "Dialog dismiss", Toast.LENGTH_SHORT)
//            .show()
//    }
//
//
//    override fun onCancel(dialog: DialogInterface) {
//        super.onCancel(dialog)
//        Toast.makeText(requireContext(), "Dialog Cancelled", Toast.LENGTH_SHORT)
//            .show()
//    }


    companion object {
        const val TAG = "PhotoBottomSheetDialogFragment"

    }


}