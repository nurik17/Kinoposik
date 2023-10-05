package com.example.kinopoisk.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.databinding.MainItemBinding
import com.example.kinopoisk.ui.home.presentation.HomeFragment

class VerticalAdapter(private val fragment : HomeFragment) :
    RecyclerView.Adapter<VerticalViewHolder>(){

    private var movieList : List<List<Movie>> = emptyList()

    fun setMovies(movies : List<List<Movie>>){
        movieList  = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        return VerticalViewHolder(
            MainItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val horizontalAdapter = MovieListAdapter { _ -> } // onItemClick navigation to next screen
        holder.binding.rvMovieChild.adapter = horizontalAdapter
        horizontalAdapter.submitList(movieList[position].take(10))

        val bundle = Bundle()
        val argsList = arrayListOf<String>()

        if(movieList[position].isNotEmpty()){
            when(movieList[position]){
                movieList[0] ->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.premieres)
                }
                movieList[1] ->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.popular)
                }
                movieList[2] ->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.top_250)
                }
                movieList[3]->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.release)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}

class VerticalViewHolder(val binding : MainItemBinding) : RecyclerView.ViewHolder(binding.root)
