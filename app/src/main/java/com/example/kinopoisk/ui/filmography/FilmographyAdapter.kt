package com.example.kinopoisk.ui.filmography

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.ItemFilmographyBinding
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.ui.home.adapter.DiffUtilCallback

class FilmographyAdapter(
    private val onClick: (Movie) -> Unit
) : ListAdapter<Movie, FilmographyViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyViewHolder {
        return FilmographyViewHolder(
            ItemFilmographyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClick) // здесь нельзя реализовать код потму что SOLID
        }
    }

class FilmographyViewHolder(val binding: ItemFilmographyBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : Movie,onClick: (Movie) -> Unit){
        binding.apply {
            movieName.text = item.nameRu ?: item.nameEn
            movieGenre.text = item.description
            rating.text = item.rating ?: "6.5"

            root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }
}
