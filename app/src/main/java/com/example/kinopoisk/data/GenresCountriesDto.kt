package com.example.kinopoisk.data

data class GenresCountriesDto(
    val countries: List<FilterCountryDto>,
    val genres: List<FilterGenreDto>
)