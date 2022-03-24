package com.example.trackinggoals.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.Navigator
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.ItemNoteBinding
import com.example.trackinggoals.model.*

interface NoteActionListener {
    fun addIncomingInNote(noteIncoming: NoteIncoming)
}

class NoteAdapter(private val navigator: Navigator,private val noteActionListener: NoteActionListener) : RecyclerView.Adapter<NoteAdapter.Holder>(),View.OnClickListener {

    private lateinit var adapterIncoming:IncomingAdapter

    var notesIncoming: List<NoteIncoming> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val noteIncoming = v.tag as NoteIncoming
        noteActionListener.addIncomingInNote(noteIncoming)

//        when (v.id) {
//            R.id.imagePlusItemNote -> {
//                noteActionListener.addIncomingInNote(noteIncoming)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)

        adapterIncoming = IncomingAdapter(navigator,object : IncomingActionListener {
            override fun onIncomingDetails(incoming: Incoming) {
                if (incoming.idIm!==1){
                    navigator.showIncoming(incoming)
                }
            }
        })
        val layoutManager = LinearLayoutManager(parent.context)
        binding.recyclerIncoming.layoutManager = layoutManager

        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val noteIncoming = notesIncoming[position]

        with(holder.binding) {
            holder.itemView.tag = noteIncoming
            imagePlusItemNote.tag = noteIncoming
            textViewItemNoteDayWeek.text = noteIncoming.note.currentData
            adapterIncoming.listIncoming = noteIncoming.listIncoming
            recyclerIncoming.adapter = adapterIncoming
        }
    }

    override fun getItemCount(): Int = notesIncoming.size


    class Holder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root)
}