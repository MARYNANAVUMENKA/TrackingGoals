package com.example.trackinggoals.screens.tab.goals.list

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.ItemGoalsBinding
import com.example.trackinggoals.model.goals.entities.Goals
import java.io.File

interface GoalsActionListener {

    fun onEditGoals(goals: Goals)
    fun onEditStatusGoals(goals: Goals)
    fun onRemoveGoals(goals: Goals)
    fun openDialogEditProgress(goals: Goals)

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
            R.id.imagePlusGoals -> {
                actionListener.openDialogEditProgress(goals)
            }
            else -> {
                actionListener.openDialogEditProgress(goals)
//                actionListener.onEditGoals(goals)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGoalsBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imageButtonMore.setOnClickListener(this)
        binding.imagePlusGoals.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val goals = goals[position]

        with(holder.binding) {
            holder.itemView.tag = goals
            imageButtonMore.tag = goals
            imagePlusGoals.tag = goals
            textViewDescription.text = goals.textGoals
            textViewTitleCriteria.text = goals.criterion
            textViewDataTitle.text = goals.dataExecution
            textViewQuantityTotal.text = goals.quantity.toString()
            if (goals.quantity != 0 && goals.progress.toFloat() < goals.quantity.toFloat() && goals.progress.toFloat() > 0.0F) {
                slider.valueFrom = 0.0F
                slider.value = goals.progress.toFloat()
                slider.valueTo = goals.quantity.toFloat()
            } else if (goals.progress.toFloat() > goals.quantity.toFloat()) {
                slider.valueFrom = 0.0F
                slider.value = goals.progress.toFloat()
                slider.valueTo = goals.progress.toFloat()
            } else if (goals.progress.toFloat() == goals.quantity.toFloat()) {
                slider.valueFrom = 0.0F
                slider.value = goals.quantity.toFloat()
                slider.valueTo = goals.quantity.toFloat()
            } else if (goals.quantity == 0) {
                slider.valueFrom = 0.0F
                slider.valueTo = 0.1F
            } else if (goals.progress.toFloat() < goals.quantity.toFloat() && goals.progress.toFloat() < 0) {
                slider.valueFrom = 0.0F
                slider.valueTo = 0.1F
            }

            textViewQuantityUnit.text = goals.unit
            textViewQuantityNow.text = goals.progress.toString()
            if (goals.quantity == 0) {
                textViewQuantityPercent.text = "(0 %)"
            } else {
                val percent = (goals.progress * 100) / goals.quantity
                textViewQuantityPercent.text = "($percent %)"
            }

            if (goals.photo.substringBefore(':') == "https") {
                Glide.with(imageViewGoalsPic.context)
                    .load(goals.photo)
                    .centerCrop()
                    .into(imageViewGoalsPic)
            } else {
                val file = readFile(imageViewGoalsPic.context, goals.photo)
                Glide.with(imageViewGoalsPic.context)
                    .asBitmap()
                    .load(file)
                    .centerCrop()
                    .into(imageViewGoalsPic)
            }

        }
    }

    override fun getItemCount(): Int = goals.size

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val goals = view.tag as Goals
        popupMenu.menu.add(0, ID_EDIT, Menu.NONE, context.getString(R.string.popupmenu_goals_edit))
        popupMenu.menu.add(
            0,
            ID_EDIT_STATUS,
            Menu.NONE,
            context.getString(R.string.popupmenu_goals_accomplished)
        )
        popupMenu.menu.add(
            0,
            ID_REMOVE,
            Menu.NONE,
            context.getString(R.string.popupmenu_goals_remove)
        )
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

    private fun readFile(context: Context, fileName: String): File {
        return File(context.filesDir, fileName)
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