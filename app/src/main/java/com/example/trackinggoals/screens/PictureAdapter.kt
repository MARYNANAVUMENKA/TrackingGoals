package com.example.trackinggoals.screens


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trackinggoals.Navigator
import com.example.trackinggoals.R

import com.example.trackinggoals.databinding.ItemPictureBinding

interface PictureActionListener {
    fun selectPicture(pictureValues: PictureValues)
}

class PictureAdapter(
    private val navigator: Navigator,
    private val pictureActionListener: PictureActionListener
) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>(), View.OnClickListener {

    var picturesValues: List<PictureValues> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val pictureValues = v.tag as PictureValues

        when (v.id) {
            R.id.imageButtonDone -> {
                pictureActionListener.selectPicture(pictureValues)
            }
            else -> {
                pictureActionListener.selectPicture(pictureValues)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPictureBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.imageButtonDone.setOnClickListener(this)

        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val pictureValue = picturesValues[position]

        with(holder.binding) {
            holder.itemView.tag = pictureValue
            imageButtonDone.tag=pictureValue
            Glide.with(imageWithGlide.context)
                .load(pictureValue.path)
                .centerCrop()
                .into(imageWithGlide)
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = picturesValues.size


    class PictureViewHolder(
        val binding: ItemPictureBinding
    ) : RecyclerView.ViewHolder(binding.root)
}