package com.example.kinopoisk.entity

interface SimilarFilms {
    val items: List<SimilarsItem>
    val total: Int
}