package com.example.kinopoisk.entity

interface Pictures {
    val items: List<PicturesItem>
    val total: Int
    val totalPages: Int
}