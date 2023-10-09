package com.example.kinopoisk.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.ItemCastBinding
import com.example.kinopoisk.entity.StaffItem

class StaffAdapter : ListAdapter<StaffItem,StaffViewHolder>(StaffDiffUtilCallback()) {
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
        holder.binding.apply {
            actorName.text = item.nameRu
            profession.text = item.professionText

            Glide.with(imageView)
                .load(item.posterUrl)
                .into(imageView)

        }
    }
}