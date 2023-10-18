package com.example.kinopoisk.data

import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.MovieSearch

class MovieSearchDto(
    override val items: List<Movie>,
    override val total: Int,
    override val totalPages: Int
) : MovieSearch