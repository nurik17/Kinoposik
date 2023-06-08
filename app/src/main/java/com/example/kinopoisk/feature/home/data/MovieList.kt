package com.example.kinopoisk.feature.home.data

data class MovieList(
    val items: List<Movie>,
    val total: Int
)

data class ChildMovieItem(
    val title: String,
    val items: List<Movie>
)