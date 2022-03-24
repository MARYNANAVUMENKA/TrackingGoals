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
import com.example.trackinggoals.model.Incoming
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator

class IncomingFragment() : Fragment() {
    private lateinit var binding: FragmentNoteIncomingBinding
    private val viewModel by viewModelCreator { IncomingViewModel(Repositories.noteRepository) }
    private lateinit var currentIncoming: Incoming

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val incomingId = requireArguments().getInt(ARG_INCOMING_ID)
        val noteId = requireArguments().getInt(ARG_NOTE_ID)
        val currentDataIn=requireArguments().getString(ARG_INCOMING_DATA)

        if (currentDataIn != null) {
            viewModel.loadIncoming(incomingId,noteId,currentDataIn)
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

        viewModel.incoming.observe(viewLifecycleOwner){
            currentIncoming = it
        }
        viewModel.currentData.observe(viewLifecycleOwner) {
            binding.textViewData.text = it
        }
        viewModel.textMessage.observe(viewLifecycleOwner) {
            binding.editTextIncoming.setText(it)
        }

        binding.imageViewBack.setOnClickListener {
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    navigator().goBaseMenu()
                }
            }
        }

        binding.imageViewDone.setOnClickListener {
            when (binding.editTextIncoming.text.toString()) {
                "" -> showAlertDialogBack()
                else -> {
                    updateIncoming()
                    navigator().goBaseMenu()
                }
            }
        }



        binding.imageViewDelete.setOnClickListener {
            showAlertDialogDelete()
        }
//        binding.imageViewPicNewNote
//        binding.imageViewGoalsNewNote



    }

    private fun updateIncoming() {
        val textMessages = binding.editTextIncoming.text.toString()
        viewModel.updateIncoming(textMessages,currentIncoming.idIm)
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

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteIncoming(currentIncoming)
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
        private const val ARG_INCOMING_ID = "ARG_INCOMING_ID"
        private const val ARG_INCOMING_DATA = "ARG_INCOMING_DATA"

        fun newInstance(incomingId: Int, noteId: Int,  currentDataIn: String): IncomingFragment {
            val fragment = IncomingFragment()
            fragment.arguments = bundleOf(ARG_INCOMING_ID to incomingId, ARG_NOTE_ID to noteId,ARG_INCOMING_DATA to currentDataIn)
            return fragment
        }
    }
}
