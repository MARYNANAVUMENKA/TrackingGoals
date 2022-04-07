package com.example.trackinggoals.screens.note

import android.app.DatePickerDialog
import android.icu.text.ConstrainedFieldPosition
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.FragmentNoteListBinding
import android.view.*
import com.example.trackinggoals.*
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.model.Repositories
import java.util.*
import kotlin.properties.Delegates


class NoteListFragment : Fragment() {
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var adapter: NoteAdapter
    private var position = 0


    private val viewModel by viewModelCreator { NoteListViewModel(Repositories.noteRepository) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.currentMonthYearLiveData.observe(viewLifecycleOwner) {
//            binding.btnToolbarNoteList.text = it
//        }

        binding.btnToolbarNoteList.setOnClickListener {
            createDataPicker()
        }

        adapter = NoteAdapter(navigator(), object : NoteActionListener {
            override fun addIncomingInNote(noteIncoming: NoteIncoming) {
                if (noteIncoming.note.id == 1) {
                    navigator().showNewNoteIncoming(noteIncoming)
                } else {
                    navigator().showIncoming(noteIncoming)
                }
            }
        })
        viewModel.listNoteIncomingLiveData.observe(viewLifecycleOwner) {
            adapter.notesIncoming = it
            binding.recyclerView.scrollToPosition(position)
            viewModel.currentDayLiveData.observe(viewLifecycleOwner) {
                position = adapter.getItemCurrentDay(it.currentData)
                val id =adapter.getItemId(position)
            }
            binding.recyclerView.scrollToPosition(position)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


    }

    fun createDataPicker() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val datepickerdialog = DatePickerDialog(requireActivity(),
            { _, year, month, day ->
                val items = resources.getStringArray(R.array.month)
                val currentMonth: String = when (month) {
                    0 -> items[0]
                    1 -> items[1]
                    2 -> items[2]
                    3 -> items[3]
                    4 -> items[4]
                    5 -> items[5]
                    6 -> items[6]
                    7 -> items[7]
                    8 -> items[8]
                    9 -> items[9]
                    10 -> items[10]
                    else -> items[11]
                }
                binding.btnToolbarNoteList.text = "$currentMonth, $year"
                viewModel.loadListNoteWithIncoming(year, month, day)
            }, y, m, d
        )
        datepickerdialog.show()
    }


    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }
}