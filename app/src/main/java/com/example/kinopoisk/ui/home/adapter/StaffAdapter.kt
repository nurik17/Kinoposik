package com.example.kinopoisk.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.ItemCastBinding
import com.example.kinopoisk.entity.StaffItem

class StaffAdapter(
    private val onClick : (StaffItem,ImageView) -> Unit
) : ListAdapter<StaffItem, StaffViewHolder>(StaffDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        return StaffViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClick)
    }
}