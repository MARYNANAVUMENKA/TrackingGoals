package com.example.trackinggoals.screens

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.FragmentNoteListBinding
import android.view.*
import com.example.trackinggoals.*
import com.example.trackinggoals.model.NoteWithIncoming
import com.example.trackinggoals.model.Repositories
import java.util.*


class NoteListFragment : Fragment() {
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var adapter: NoteAdapter



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
        viewModel.currentMonthYearLiveData.observe(viewLifecycleOwner){
            binding.btnToolbarNoteList.text=it
        }

        binding.btnToolbarNoteList.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val datepickerdialog = DatePickerDialog(requireActivity(),
                { _, year, month, day ->
                    val items = resources.getStringArray(R.array.month)
                    val currentMonth:String = when(month){
                        0->items[0]
                        1->items[1]
                        2-> items[2]
                        3->items[3]
                        4->items[4]
                        5->items[5]
                        6->items[6]
                        7->items[7]
                        8-> items[8]
                        9->items[9]
                        10->items[10]
                        else ->items[11]
                    }
                    binding.btnToolbarNoteList.text = "$currentMonth, $year"
                    viewModel.loadListNoteWithIncoming(year,month,day)
                }, y, m, d)
            datepickerdialog.show()
        }

        adapter = NoteAdapter(object : NoteActionListener {
            override fun onNewNoteDetails(noteWithIncoming: NoteWithIncoming) {
                navigator().showNewNote(noteWithIncoming)
            }

        })
        viewModel.listNoteWithIncomingLiveData.observe(viewLifecycleOwner) {
            adapter.notesWithIncoming = it
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

    }



    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }
}