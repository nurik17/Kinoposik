package com.example.kinopoisk.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.ItemCastBinding
import com.example.kinopoisk.entity.StaffItem

class CastAdapter(
    private val onClick : (StaffItem,ImageView) -> Unit
): ListAdapter<StaffItem, StaffViewHolder>(StaffDiffUtilCallback()){
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
            root.setOnClickListener {
                onClick.invoke(item,imageView)
            }
        }
    }
}

class StaffDiffUtilCallback : DiffUtil.ItemCallback<StaffItem>(){
    override fun areItemsTheSame(oldItem: StaffItem, newItem: StaffItem): Boolean {
        return oldItem.staffId == newItem.staffId
    }

    override fun areContentsTheSame(oldItem: StaffItem, newItem: StaffItem): Boolean {
        return oldItem == newItem
    }

}

class StaffViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)
