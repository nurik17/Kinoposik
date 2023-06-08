package com.example.kinopoisk.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.databinding.ParentItemBinding
import com.example.kinopoisk.feature.home.data.ChildMovieItem
import com.example.kinopoisk.feature.home.data.Movie
import com.example.kinopoisk.feature.home.data.MovieList
import kotlin.coroutines.coroutineContext

class ParentAdapter: RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    var itemMovieList : List<ChildMovieItem> = ArrayList()

    inner class ParentViewHolder(val binding: ParentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result : ChildMovieItem){
            val childAdapter = ChildAdapter(result.items)
            binding.rvMovieChild.layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
            binding.rvMovieChild.adapter = childAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ParentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ParentViewHolder(binding)
    }

    override fun getItemCount(): Int = itemMovieList.size

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(itemMovieList[position])
    }

    fun addData(list : List<Movie>){
        val moviesByYear = list.groupBy { it.year.toString() }
        itemMovieList =moviesByYear.map {
            ChildMovieItem(
                title = it.key,
                items = it.value
            )
        }
    }
}