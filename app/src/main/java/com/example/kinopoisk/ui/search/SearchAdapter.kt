package com.example.kinopoisk.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.SearchItemBinding
import com.example.kinopoisk.entity.Genre
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.ui.home.adapter.DiffUtilCallback

class SearchAdapter: PagingDataAdapter<Movie, SearchViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            movieName.text = item?.nameRu
            movieGenre.text = getFirstGenre(item?.genres ?: emptyList())

            Glide.with(imageView)
                .load(item?.posterUrl)
                .into(imageView)
        }
    }
    private fun getFirstGenre(genre: List<Genre>): String {
        return genre.firstOrNull()?.genre ?: ""
    }
}

class SearchViewHolder(val binding : SearchItemBinding) : RecyclerView.ViewHolder(binding.root)
