package com.example.trackinggoals.screens.note


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.Navigator
import com.example.trackinggoals.databinding.ItemNoteBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.notes.entities.NoteIncoming
import com.example.trackinggoals.screens.incoming.IncomingActionListener
import com.example.trackinggoals.screens.incoming.IncomingAdapter


interface NoteActionListener {
    fun addIncomingInNote(noteIncoming: NoteIncoming)
}

class NoteAdapter(
    private val navigator: Navigator,
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
        adapterIncoming = IncomingAdapter(navigator, object : IncomingActionListener {
            override fun onIncomingDetails(incoming: Incoming) {
                if (incoming.idIm !== 1) {
                    navigator.showIncoming(incoming)
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
            recyclerIncoming.adapter = adapterIncoming

            if (noteIncoming.listIncoming.isEmpty()) {
                recyclerIncoming.layoutParams = RecyclerView.LayoutParams(378, 230)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = notesIncoming.size


    class Holder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
