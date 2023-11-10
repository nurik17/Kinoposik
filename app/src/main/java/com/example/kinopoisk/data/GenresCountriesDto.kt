package com.example.kinopoisk.data

import com.example.kinopoisk.entity.FilterCountry
import com.example.kinopoisk.entity.FilterGenre

data class GenresCountriesDto(
    val countries: List<FilterCountry>,
    val genres: List<FilterGenre>
)