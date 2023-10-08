package com.example.kinopoisk.ui.home.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.databinding.MainItemBinding
import com.example.kinopoisk.entity.onItemClick
import com.example.kinopoisk.ui.home.presentation.HomeFragment

class VerticalAdapter(private val fragment : HomeFragment) :
    RecyclerView.Adapter<VerticalViewHolder>(){

    private var movieList : List<List<Movie>> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
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
        val horizontalAdapter = MovieListAdapter { movie ->
            onItemClick(movie,fragment)
        }
        holder.binding.rvMovieChild.adapter = horizontalAdapter
        horizontalAdapter.submitList(movieList[position].take(10))

        val bundle = Bundle()
        val argsList = arrayListOf<String>()

        if(movieList[position].isNotEmpty()){
            when(movieList[position]){
                movieList[0] ->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.premieres)
                    holder.binding.tvAll.setOnClickListener {
                        argsList.add("premieres")
                        bundle.putStringArrayList("1",argsList)
                        fragment.findNavController().navigate(R.id.fullMovieFragment,bundle)
                    }
                }
                movieList[1] ->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.popular)
                    holder.binding.tvAll.setOnClickListener {
                        argsList.add("top100")
                        bundle.putStringArrayList("1",argsList)
                        fragment.findNavController().navigate(R.id.fullMovieFragment,bundle)
                    }
                }
                movieList[2] ->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.top_250)
                    holder.binding.tvAll.setOnClickListener {
                        argsList.add("top250")
                        bundle.putStringArrayList("1",argsList)
                        fragment.findNavController().navigate(R.id.fullMovieFragment,bundle)
                    }
                }
                movieList[3]->{
                    holder.binding.tvPremier.text = holder.binding.root.context.getString(R.string.release)
                    holder.binding.tvAll.setOnClickListener {
                        argsList.add("release")
                        bundle.putStringArrayList("1",argsList)
                        fragment.findNavController().navigate(R.id.fullMovieFragment,bundle)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}

class VerticalViewHolder(val binding : MainItemBinding) : RecyclerView.ViewHolder(binding.root)
