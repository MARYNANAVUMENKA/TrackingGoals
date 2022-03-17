package com.example.trackinggoals.screens


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentNoteIncomingBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator

class IncomingFragment() : Fragment() {
    private lateinit var binding: FragmentNoteIncomingBinding
    private val viewModel by viewModelCreator { IncomingViewModel(Repositories.noteRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteId = requireArguments().getInt(ARG_NOTE_ID)
        val currentData = requireArguments().getString(ARG_NOTE_DATA)
        if (currentData != null) {
            viewModel.loadNoteWithIncoming(noteId, currentData)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteIncomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextIncoming.requestFocus()

        binding.imageViewDone.setOnClickListener {
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    updateNoteWithIncoming()
                    navigator().goBaseMenu()
                }
            }
        }

        binding.imageViewBack.setOnClickListener {
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    updateNoteWithIncoming()
                    navigator().goBaseMenu()
                }
            }
        }

        binding.imageViewDelete.setOnClickListener {
            showAlertDialogDelete()
        }
//        binding.imageViewPicNewNote
//        binding.imageViewGoalsNewNote


        viewModel.currentData.observe(viewLifecycleOwner) {
            binding.textViewData.text = it
        }
        viewModel.textMessage.observe(viewLifecycleOwner) {
            binding.editTextIncoming.setText(it)
        }

    }


    private fun updateNoteWithIncoming() {
        val textIncoming = binding.editTextIncoming.text.toString()
        val noteId = requireArguments().getInt(ARG_NOTE_ID)
        val currentData = requireArguments().getString(ARG_NOTE_DATA)
        if (noteId == 1) {
            if (currentData != null) {
                viewModel.saveNoteWithIncoming(textIncoming, noteId, currentData)
            }
        } else {
            if (currentData != null) {
                viewModel.editNoteWithIncoming(textIncoming, noteId, currentData)
            }
        }
    }

    private fun showAlertDialogBack() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> navigator().goBaseMenu()
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_exit_title)
            .setMessage(R.string.alert_dialog_exit_text)
            .setPositiveButton(R.string.alert_dialog_action_exit, listener)
            .setNegativeButton(R.string.alert_dialog_action_return, listener)
            .create()
        dialog.show()
    }

    private fun showAlertDialogDelete() {
        val noteId = requireArguments().getInt(ARG_NOTE_ID)
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteNoteWithIncoming(noteId)
                    navigator().goBaseMenu()
                }
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_delete_title)
            .setMessage(R.string.alert_dialog_delete_text)
            .setPositiveButton(R.string.alert_dialog_action_yes, listener)
            .setNegativeButton(R.string.alert_dialog_action_no, listener)
            .create()
        dialog.show()
    }


    companion object {
        private const val ARG_NOTE_ID = "ARG_NOTE_ID"
        private const val ARG_NOTE_DATA = "ARG_NOTE_DATA"

        fun newInstance(noteId: Int, currentData: String): IncomingFragment {
            val fragment = IncomingFragment()
            fragment.arguments = bundleOf(ARG_NOTE_ID to noteId, ARG_NOTE_DATA to currentData)
            return fragment
        }
    }
}
