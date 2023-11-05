package com.example.kinopoisk.data

import com.example.kinopoisk.entity.FilterCountry

class FilterCountryDto(
    override val country: String,
    override val id: Int
) : FilterCountry