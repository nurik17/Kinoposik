package com.example.kinopoisk.feature.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.MovieChildItemBinding
import com.example.kinopoisk.feature.home.data.Movie
import com.example.kinopoisk.feature.home.data.MovieList


class ChildAdapter(var movie : List<Movie>): RecyclerView.Adapter<ChildAdapter.ChildViewHolder>(){
    private var movieList :List<Movie> = ArrayList()



    init {
        this.movieList = movie
    }

    inner class ChildViewHolder(val binding: MovieChildItemBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("CheckResult")
        fun bind(result : Movie){
            binding.filmNameText.text = result.nameEn
            binding.filmGenreText.text = result.genres.toString()
            Glide.with(itemView.context).load(result.posterUrlPreview).into(binding.image)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = MovieChildItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movie.size
    }

}