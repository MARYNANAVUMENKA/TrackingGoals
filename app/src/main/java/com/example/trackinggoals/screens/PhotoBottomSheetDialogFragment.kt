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