package com.example.trackinggoals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentNoteIncomingBinding


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

//        binding.editTextNewNote.setOnClickListener {}

        binding.imageViewDone.setOnClickListener {
            val textIncoming = binding.editText.text.toString()
            if (textIncoming.isNotEmpty()) {
                val noteId = requireArguments().getInt(ARG_NOTE_ID)
                val currentData = requireArguments().getString(ARG_NOTE_DATA)
                if (currentData != null) {
                    viewModel.saveNoteWithIncoming(textIncoming, noteId, currentData)
                }
            }else{
                val noteId = requireArguments().getInt(ARG_NOTE_ID)
                val currentData = requireArguments().getString(ARG_NOTE_DATA)
                if (currentData != null) {
                    viewModel.deleteNoteWithIncoming(textIncoming, noteId, currentData)
                }
                }

                navigator().goBack()

            }

        binding.imageViewBack.setOnClickListener {

        }
        binding.imageViewDelete.setOnClickListener {

        }
//        binding.imageViewPicNewNote
//        binding.imageViewGoalsNewNote


        viewModel.currentData.observe(viewLifecycleOwner) {
            binding.btnData.text=it
        }
        viewModel.textMessage.observe(viewLifecycleOwner){
            binding.editText.setText(it)
        }



        return binding.root
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
