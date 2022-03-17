package com.example.trackinggoals.screens

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.ItemGoalsBinding
import com.example.trackinggoals.databinding.ItemNoteBinding
import com.example.trackinggoals.model.Goals

interface GoalsActionListener {

    fun onEditGoals(goals: Goals)
    fun onEditStatusGoals(goals: Goals)
    fun onRemoveGoals(goals: Goals)

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
                showPopupMenu(v)
            }
//            else -> {
//                actionListener.onEditGoals(goals)
//            }
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
            textViewDescription.text=goals.textGoals
            textViewTitleCriteria.text=goals.criterion
            textViewDataTitle.text = goals.dataExecution
            textViewQuantityTotal.text= goals.quantity.toString()
            textViewQuantityUnit.text=goals.unit
            textViewQuantityNow.text="0"
            textViewQuantityPercent.text="0%"

        }
    }

    override fun getItemCount(): Int = goals.size

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val goals = view.tag as Goals



        popupMenu.menu.add(0, ID_EDIT, Menu.NONE, context.getString(R.string.popupmenu_goals_edit))
        popupMenu.menu.add(0, ID_EDIT_STATUS, Menu.NONE, context.getString(R.string.popupmenu_goals_accomplished))
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.popupmenu_goals_remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_EDIT -> {
                    actionListener.onEditGoals(goals)
                }
                ID_EDIT_STATUS -> {
                    actionListener.onEditStatusGoals(goals)
                }
                ID_REMOVE -> {
                    actionListener.onRemoveGoals(goals)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }


    class Holder(
        val binding: ItemGoalsBinding
    ) : RecyclerView.ViewHolder(binding.root)
    companion object {
        private const val ID_EDIT = 1
        private const val ID_EDIT_STATUS = 2
        private const val ID_REMOVE = 3
    }
}