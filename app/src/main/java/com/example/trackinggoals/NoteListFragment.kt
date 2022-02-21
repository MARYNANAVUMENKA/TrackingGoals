package com.example.trackinggoals

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.databinding.FragmentNoteListBinding
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
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
                    val currentMonth:String=when(month){
                        0->"Январь"
                        1->"Февраль"
                        2-> "Март"
                        3->"Апрель"
                        4->"Май"
                        5->"Июнь"
                        6->"Июль"
                        7->"Август"
                        8-> "Сентябрь"
                        9->"Октябрь"
                        10->"Ноябрь"
                        else ->"Декабрь"
                    }
                    binding.btnToolbarNoteList.text = "$currentMonth, $year"
                    viewModel.loadListNote(year,month,day)

                }, y, m, d)
            datepickerdialog.show()
        }

        adapter = NoteAdapter(object : NoteActionListener{
            override fun onNewNoteDetails(note: Note) {
                navigator().showNewNote(note)
            }

        })
        viewModel.listNoteLiveData.observe(viewLifecycleOwner) {
            adapter.notes = it
        }
//        viewModel.listIncomingLiveData.observe(viewLifecycleOwner){
//            adapter.notes=it
//        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        return binding.root
        //        binding.btnToolbar.text="ffffg"
//        binding.btnToolbar.text
//
//        }
//        binding.toolbar.inflateMenu(R.menu.toolbar_menu_note_list)
//        val searchItem = binding.toolbar.menu.findItem(R.id.item_menu_toolbar)
//
//        val searchView = searchItem?.actionView

//        searchView.setOnClickListener {
//            Toast.makeText(requireContext(), "R.string.error_auth", Toast.LENGTH_SHORT)
//                .show()
//        }
//        binding.toolbar.setOnMenuItemClickListener {
//            onOptionsItemSelected(it)
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater(R.menu.toolbar_menu_note_list, menu)
//
//        val searchItem = menu?.findItem(R.id.item_menu_toolbar)
//        val searchView = searchItem?.actionView as Button
//
//        // Configure the search info and add any event listeners...
//
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.item_menu_toolbar -> {
//                Toast.makeText(requireContext(), "R.string.error_auth", Toast.LENGTH_SHORT)
//                    .show()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }
}