package com.example.kinopoisk.data

data class ParamsFilterFilm(
    val countries: Map<Int, String> = emptyMap(),
    val genres: Map<Int, String> = emptyMap(),
    val order: String = "RATING",
    val type: String = "",
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val yearFrom: Int = 2000,
    val yearTo: Int = 2023,
    val imdbId: String? = null,
    val keyword: String = ""
)

data class ParamsFilterGallery(
    val filmId: Int = 328,
    val galleryType: String = "STILL"
)
