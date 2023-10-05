package com.example.kinopoisk.data

import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.MovieList

class MovieListDto(
    override val items : List<Movie>,
    override val total : Int
) : MovieList