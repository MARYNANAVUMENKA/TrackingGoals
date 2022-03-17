package com.example.trackinggoals.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trackinggoals.databinding.BottomSheetEditGoalsFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditGoalsBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetEditGoalsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=BottomSheetEditGoalsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewBottomSheetGoalsEdit.setOnClickListener {  }
        binding.textViewBottomSheetGoalsAccomplished.setOnClickListener {  }
        binding.textViewBottomSheetGoalsRemove.setOnClickListener {  }
        binding.textViewSheetGoalsCancel.setOnClickListener { dismiss() }
    }

}