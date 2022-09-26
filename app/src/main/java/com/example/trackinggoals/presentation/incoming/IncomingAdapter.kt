package com.example.trackinggoals.presentation.incoming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.databinding.ItemIncomingBinding
import com.example.trackinggoals.domain.model.Incoming


interface IncomingActionListener {
    fun onIncomingDetails(incoming: Incoming)
}

class IncomingAdapter(
    private val actionListenerIncoming: IncomingActionListener
) :
    RecyclerView.Adapter<IncomingAdapter.Holder>(), View.OnClickListener {

    var listIncoming: List<Incoming> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View?) {
        val incoming = v?.tag as Incoming
        actionListenerIncoming.onIncomingDetails(incoming)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIncomingBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val incoming = listIncoming[position]
        with(holder.binding) {
            holder.itemView.tag = incoming
            textViewItemIncomingNewResult.text = incoming.textMessages
            textViewItemIncomingProgress.text = incoming.quantity
        }
    }

    override fun getItemCount(): Int = listIncoming.size

    class Holder(
        val binding: ItemIncomingBinding
    ) : RecyclerView.ViewHolder(binding.root)

}