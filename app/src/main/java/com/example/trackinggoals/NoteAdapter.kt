package com.example.trackinggoals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.databinding.ItemNoteBinding

interface NoteActionListener {

    fun onNewNoteDetails(note: Note)

}

class NoteAdapter(private val actionListener: NoteActionListener) :
    RecyclerView.Adapter<NoteAdapter.Holder>(), View.OnClickListener {

    var notes: List<Note> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val note = v.tag as Note
        when (v.id) {
            R.id.imagePlus -> {
                actionListener.onNewNoteDetails(note)
            }
            else -> {
                actionListener.onNewNoteDetails(note)
            }
        }

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imagePlus.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val note = notes[position]

        with(holder.binding) {
            holder.itemView.tag = note
            imagePlus.tag = note
            textDayWeek.text = note.currentData
        }
    }

    override fun getItemCount(): Int = notes.size

    class Holder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root)
}