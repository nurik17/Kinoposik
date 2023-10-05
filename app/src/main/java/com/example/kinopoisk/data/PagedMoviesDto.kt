package com.example.kinopoisk.data

import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.PagedMovies

class PagedMoviesDto(
    override val pagesCount : Int,
    override val films : List<Movie>
) : PagedMovies