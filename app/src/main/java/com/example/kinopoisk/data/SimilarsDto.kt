package com.example.kinopoisk.data

import com.example.kinopoisk.entity.SimilarFilms
import com.example.kinopoisk.entity.SimilarsItem

class SimilarsDto(
    override val items: List<SimilarsItem>,
    override val total: Int
) : SimilarFilms