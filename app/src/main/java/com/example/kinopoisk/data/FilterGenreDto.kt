package com.example.kinopoisk.data

import com.example.kinopoisk.entity.FilterGenre

data class FilterGenreDto(
    override val genre: String,
    override val id: Int
) : FilterGenre