package com.example.trackinggoals.screens.tab.note

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.FragmentNoteListBinding
import android.view.*
import com.example.trackinggoals.*
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.screens.tab.TabsFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding?=null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter
    private var position = 0
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)

    private val viewModel by viewModel<NoteListViewModel> { parametersOf() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToolbarNoteList.setOnClickListener {
            createDataPicker()
        }

        adapter = NoteAdapter(this, object : NoteActionListener {
            override fun addIncomingInNote(noteIncoming: NoteIncoming) {
                if (noteIncoming.note.id == 1) {
                    val direction = TabsFragmentDirections.actionTabsFragmentToIncomingFragment(
                        1,
                        noteIncoming.note.id,
                        noteIncoming.note.currentData
                    )
                    findTopNavController().navigate(direction)
                } else {
                    val direction = TabsFragmentDirections.actionTabsFragmentToIncomingFragment(
                        UUID.randomUUID().hashCode(),
                        noteIncoming.note.id,
                        noteIncoming.note.currentData
                    )
                    findTopNavController().navigate(direction)
                }
            }
        })
        viewModel.listNoteIncomingLiveData.observe(viewLifecycleOwner) {
            adapter.notesIncoming = it
            binding.recyclerView.scrollToPosition(position)
            viewModel.currentDayLiveData.observe(viewLifecycleOwner) {
                position = adapter.getItemCurrentDay(it.currentData)
                val id = adapter.getItemId(position)
            }
            binding.recyclerView.scrollToPosition(position)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadListNoteWithIncoming(year, month, day)
    }


    private fun createDataPicker() {
        val cal = Calendar.getInstance()
        val datepickerdialog = DatePickerDialog(
            requireActivity(),
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
                sendData(year, month, day)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        datepickerdialog.show()
    }

    private fun sendData(currentYear: Int, currentMonth: Int, currentDay: Int) {
        year = currentYear
        month = currentMonth
        day = currentDay
    }
}