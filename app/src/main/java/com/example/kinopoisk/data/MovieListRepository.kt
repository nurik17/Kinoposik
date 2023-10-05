package com.example.kinopoisk.data

import com.example.kinopoisk.domain.MovieApi
import com.example.kinopoisk.entity.Movie

class MovieListRepository(private val api: MovieApi) {

    suspend fun getPremieres(month: String,year: String) : List<Movie>{
        return api.getPremieres(month,year).items
    }

    suspend fun getPopular100(page: Int?) : List<Movie>{
        return api.getPopular100(page).films
    }
    suspend fun getTop250(page: Int?) : List<Movie>{
        return api.getTop250(page).films
    }
    suspend fun getReleases(year: String,month: String,page: Int?) : List<Movie>{
        return api.getReleases(year, month, page).releases
    }
}