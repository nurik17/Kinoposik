package com.example.kinopoisk.feature.home.repository

import com.example.kinopoisk.feature.home.data.Movie
import com.example.kinopoisk.feature.home.retrofit.retrofit
import java.time.Month


class MovieListRepository {

    suspend fun getPremieres(year :Int,month: String) : List<Movie>{
        return retrofit.movies(year,month).items
    }
}