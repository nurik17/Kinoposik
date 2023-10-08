package com.example.kinopoisk.entity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R

fun onItemClick(item:Movie,fragment : Fragment){
    val bundle = Bundle()
    val filmId = item.filmId
    val kinopoiskId = item.kinopoiskId

    bundle.putInt("filmId",filmId)
    bundle.putInt("kinopoiskId",kinopoiskId)
    fragment.findNavController().navigate(R.id.movieDetailFragment,bundle)
}