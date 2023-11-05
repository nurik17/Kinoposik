package com.example.kinopoisk.ui.home.fullMovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.FullMovieItemBinding
import com.example.kinopoisk.entity.Genre
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.ui.home.adapter.DiffUtilCallback

class MovieFullAdapter(
    private val onClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieFullAdapter.FullListViewHolder>(DiffUtilCallback()) {

    override fun onBindViewHolder(holder: FullListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClick)
        }


    private fun getFirstGenre(genre: List<Genre>): String {
        return genre.firstOrNull()?.genre ?: ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullListViewHolder {
        return FullListViewHolder(
            FullMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class FullListViewHolder(val binding: FullMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?,onClick: (Movie) -> Unit) {
            binding.apply {
                if (!item?.rating.isNullOrEmpty()) {
                    rating.visibility = View.VISIBLE
                    rating.text = item?.rating
                } else {
                    rating.visibility = View.GONE
                }
                movieName.text = item?.nameRu ?: ""
                movieGenre.text = getFirstGenre(item?.genres ?: emptyList())

                Glide.with(imageView).load(item?.posterUrlPreview).into(imageView)

                root.setOnClickListener {
                    onClick.invoke(item!!)
                }
            }
        }
        private fun getFirstGenre(genre: List<Genre>): String {
            return genre.firstOrNull()?.genre ?: ""
        }
    }
}
