package com.example.kinopoisk.entity

interface ReleaseList {
    val page: Int
    val releases: List<Movie>
    val total: Int
}