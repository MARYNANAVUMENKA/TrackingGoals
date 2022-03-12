package com.example.trackinggoals.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.ItemNoteBinding

//interface GoalsActionListener {
//
//    fun onNewNoteDetails(noteWithIncoming: NoteWithIncoming)
//
//}
//
//class GoalsAdapter(private val actionListener: GoalsActionListener) :
//    RecyclerView.Adapter<GoalsAdapter.Holder>(), View.OnClickListener {
//
//    var notesWithIncoming: List<NoteWithIncoming> = emptyList()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    override fun onClick(v: View) {
//        val noteWithIncoming = v.tag as NoteWithIncoming
//        when (v.id) {
//            R.id.imagePlus -> {
//                actionListener.onNewNoteDetails(noteWithIncoming)
//            }
//            else -> {
//                actionListener.onNewNoteDetails(noteWithIncoming)
//            }
//        }
//
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemNoteBinding.inflate(inflater, parent, false)
//
//        binding.root.setOnClickListener(this)
//        binding.imagePlus.setOnClickListener(this)
//        return Holder(binding)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val noteWithIncoming = notesWithIncoming[position]
//
//        with(holder.binding) {
//            holder.itemView.tag = noteWithIncoming
//            imagePlus.tag = noteWithIncoming
//            textDayWeek.text = noteWithIncoming.noteDbEntity.currentData
//            textViewIncoming.text = noteWithIncoming.listincomingMessages[0].textMessages
//        }
//    }
//
//    override fun getItemCount(): Int = notesWithIncoming.size
//
//    class Holder(
//        val binding: ItemNoteBinding
//    ) : RecyclerView.ViewHolder(binding.root)
//}