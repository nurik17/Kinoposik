package com.example.kinopoisk.ui.gallery

import com.example.kinopoisk.entity.PicturesItem
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.ImageItemBinding

class PicturesAdapter(
    private val onClick: (PicturesItem, ImageView) -> Unit
) : PagingDataAdapter<PicturesItem, PicturesViewHolder>(PicturesDiffUtilCallback()) {
    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            Glide.with(image)
                .load(item?.imageUrl)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        return PicturesViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class PicturesDiffUtilCallback : DiffUtil.ItemCallback<PicturesItem>() {
    override fun areItemsTheSame(oldItem: PicturesItem, newItem: PicturesItem): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: PicturesItem, newItem: PicturesItem): Boolean = oldItem == newItem
}

class PicturesViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)