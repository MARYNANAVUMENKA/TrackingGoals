package com.example.trackinggoals.presentation.constructor.picture


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.trackinggoals.databinding.ItemPictureBinding

interface PictureActionListener {
    fun selectPicture(pictureValues: PictureValues)
}

class PictureAdapter(
    private val pictureActionListener: PictureActionListener
) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>(), View.OnClickListener {

    var picturesValues: List<PictureValues> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val pictureValues = v.tag as PictureValues
        pictureActionListener.selectPicture(pictureValues)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPictureBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val pictureValue = picturesValues[position]

        with(holder.binding) {
            holder.itemView.tag = pictureValue
            Glide.with(imageWithGlide.context)
                .load(pictureValue.path)
                .centerCrop()
                .into(imageWithGlide)
        }
    }

    override fun getItemCount(): Int = picturesValues.size

    class PictureViewHolder(
        val binding: ItemPictureBinding
    ) : RecyclerView.ViewHolder(binding.root)
}