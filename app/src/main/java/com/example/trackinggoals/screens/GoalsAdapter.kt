package com.example.trackinggoals.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.ItemGoalsBinding
import com.example.trackinggoals.databinding.ItemNoteBinding
import com.example.trackinggoals.model.Goals

interface GoalsActionListener {

    fun onEditGoals(goals: Goals)
}

class GoalsAdapter(private val actionListener: GoalsActionListener) :
    RecyclerView.Adapter<GoalsAdapter.Holder>(), View.OnClickListener {

    var goals: List<Goals> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val goals = v.tag as Goals
        when (v.id) {
            R.id.imageButtonMore -> {
                actionListener.onEditGoals(goals)
            }
            else -> {
                actionListener.onEditGoals(goals)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGoalsBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imageButtonMore.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val goals = goals[position]

        with(holder.binding) {
            holder.itemView.tag = goals
            imageButtonMore.tag = goals

        }
    }

    override fun getItemCount(): Int = goals.size

    class Holder(
        val binding: ItemGoalsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}