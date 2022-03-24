package com.example.trackinggoals.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.Navigator
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.ItemIncomingBinding
import com.example.trackinggoals.model.Incoming

import com.example.trackinggoals.model.IncomingDbEntity
import com.example.trackinggoals.model.NoteWithIncoming

interface IncomingActionListener {

    fun onIncomingDetails(incoming: Incoming)

}

class IncomingAdapter(private val navigator: Navigator,private val actionListenerIncoming: IncomingActionListener) :
    RecyclerView.Adapter<IncomingAdapter.Holder>(),View.OnClickListener {



    var listIncoming: List<Incoming> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onClick(v: View?) {
        val incoming= v?.tag as Incoming
        actionListenerIncoming.onIncomingDetails(incoming)


//        when (v.id) {
//            R.id.floatingActionButtonGoalsList -> {
//                actionListenerIncoming.onNewIncoming(incoming)
//            }
//            else -> {
//                actionListenerIncoming.onIncomingDetails(incoming)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomingAdapter.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIncomingBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return IncomingAdapter.Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val incoming = listIncoming[position]
        with(holder.binding){
            holder.itemView.tag = incoming
            textViewItemIncoming.text = incoming.textMessages
        }
    }

    override fun getItemCount(): Int = listIncoming.size

    class Holder(
        val binding: ItemIncomingBinding
    ) : RecyclerView.ViewHolder(binding.root)

}