package com.example.kinopoisk.feature.home.repository

import com.example.kinopoisk.feature.home.data.Movie
import com.example.kinopoisk.feature.home.retrofit.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieListRepository {

    suspend fun getPremieres(year: Int, month: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                retrofit.movies(year, month).items
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}