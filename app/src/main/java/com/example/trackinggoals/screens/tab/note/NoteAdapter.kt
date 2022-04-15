package com.example.trackinggoals.screens.tab.note


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.databinding.ItemNoteBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.R
import com.example.trackinggoals.findTopNavController
import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.screens.incoming.IncomingActionListener
import com.example.trackinggoals.screens.incoming.IncomingAdapter
import com.example.trackinggoals.screens.tab.TabsFragmentDirections


interface NoteActionListener {
    fun addIncomingInNote(noteIncoming: NoteIncoming)
}

class NoteAdapter(
    private val parentFragment: Fragment,
    private val noteActionListener: NoteActionListener
) : RecyclerView.Adapter<NoteAdapter.Holder>(), View.OnClickListener {

    private lateinit var adapterIncoming: IncomingAdapter


    var notesIncoming: List<NoteIncoming> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val noteIncoming = v.tag as NoteIncoming
        noteActionListener.addIncomingInNote(noteIncoming)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        adapterIncoming = IncomingAdapter(object : IncomingActionListener {
            override fun onIncomingDetails(incoming: Incoming) {
                if (incoming.idIm != 1) {
                    val direction = TabsFragmentDirections.actionTabsFragmentToIncomingFragment(
                        incoming.idIm,
                        incoming.idNote,
                        incoming.currentDataIn
                    )
                    parentFragment.findTopNavController().navigate(direction)
                }
            }
        })
        val layoutManager = object : LinearLayoutManager(parent.context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.recyclerIncoming.layoutManager = layoutManager
        val holder = Holder(binding)
        holder.setIsRecyclable(false)
        binding.root.setOnClickListener(this)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val noteIncoming = notesIncoming[position]

        with(holder.binding) {
            holder.itemView.tag = noteIncoming
            imagePlusItemNote.tag = noteIncoming
            textViewItemNoteDayWeek.text = noteIncoming.note.currentData
            adapterIncoming.listIncoming = noteIncoming.listIncoming
            if (noteIncoming.note.isToday) {
                textViewItemNoteToday.text = parentFragment.resources.getString(R.string.text_today)
            }
            recyclerIncoming.adapter = adapterIncoming
            if (noteIncoming.listIncoming.isEmpty()) {
                recyclerIncoming.layoutParams = RecyclerView.LayoutParams(378, 230)
            }
        }
    }

    override fun getItemCount(): Int = notesIncoming.size

    fun getItemCurrentDay(currentData: String): Int {
        var index = 0
        for (noteIncoming in notesIncoming) {
            if (noteIncoming.note.currentData == currentData) {
                index = notesIncoming.indexOf(noteIncoming)
            }
        }
        return index
    }


    class Holder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root)

}
