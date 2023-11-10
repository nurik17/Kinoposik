package com.example.kinopoisk.ui.search.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.databinding.FiltersItemBinding
import com.example.kinopoisk.entity.FilterCountryGenre


class SearchFilterAdapter(
    private val onFilterItemClick: (FilterCountryGenre) -> Unit
) : ListAdapter<FilterCountryGenre,SearchFilterViewHolder>(DiffUtilCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchFilterViewHolder(
        FiltersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: SearchFilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onFilterItemClick)
    }
}

class SearchFilterViewHolder(val binding: FiltersItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: FilterCountryGenre, onFilterItemClick: (FilterCountryGenre) -> Unit){
        var isSelected = false
        if(item.name.isNotEmpty()){
            binding.apply {
                onFilterItemClick(item)
                searchFilterName.text = item.name
                searchFilterName.setOnClickListener {
                    isSelected = !isSelected
                    if(isSelected){
                        searchFilterName.setBackgroundColor(Color.DKGRAY)
                    }else{
                        searchFilterName.setBackgroundColor(Color.WHITE)
                    }
                }
            }
        }
    }
}
class DiffUtilCallback() : DiffUtil.ItemCallback<FilterCountryGenre>(){
    override fun areItemsTheSame(
        oldItem: FilterCountryGenre,
        newItem: FilterCountryGenre
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FilterCountryGenre,
        newItem: FilterCountryGenre
    ): Boolean {
        return oldItem == newItem
    }

}