package com.example.kinopoisk.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.databinding.ChildItemBinding
import com.bumptech.glide.Glide
import com.example.kinopoisk.entity.Genre

class MovieListAdapter(
    private val onClick: (Movie) -> Unit
) : ListAdapter<Movie, DiffUtilCallback.MovieListViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffUtilCallback.MovieListViewHolder {
        return DiffUtilCallback.MovieListViewHolder(
            ChildItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiffUtilCallback.MovieListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            if(item.ratingKinopoisk != 0.0){
                rating.visibility = View.VISIBLE
                rating.text = item.ratingKinopoisk.toString()
            }else if(!item.rating.isNullOrEmpty()){
                rating.visibility = View.VISIBLE
                rating.text = item.rating
            }else{
                rating.visibility = View.GONE
            }
            movieName.text = item?.nameRu ?: ""
            movieGenre.text = getFirstGenre(item.genres ?: emptyList())
            Glide.with(imageView)
                .load(item.posterUrlPreview)
                .into(imageView)

            root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }

    private fun getFirstGenre(genre: List<Genre>): String {
        return genre.firstOrNull()?.genre ?: ""
    }
}


class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
}

class MovieListViewHolder(val binding: ChildItemBinding) : RecyclerView.ViewHolder(binding.root)

}