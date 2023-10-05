package com.example.kinopoisk.entity

interface PagedMovies{
    val pagesCount : Int
    val films : List<Movie>
}