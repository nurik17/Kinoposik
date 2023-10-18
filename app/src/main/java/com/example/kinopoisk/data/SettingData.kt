package com.example.kinopoisk.data

data class SettingData(
    var selectedCountry : Int? = null,
    var selectedGenre : Int? = null,
    var selectedOrder : String = "YEAR",
    var selectedType : String = "ALL",
    var yearFrom : Int = 1960,
    var yearTo : Int = 2023,
    var ratingFrom : Int = 0,
    var ratingTo : Int = 10,
)