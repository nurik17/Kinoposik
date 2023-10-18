package com.example.kinopoisk.entity

interface MovieSearch {
    val items: List<Movie>
    val total: Int
    val totalPages: Int
}