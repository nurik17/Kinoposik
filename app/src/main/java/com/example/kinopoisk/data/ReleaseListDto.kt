package com.example.kinopoisk.data

import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.ReleaseList

class ReleaseListDto(
    override val page: Int,
    override val releases: List<Movie>,
    override val total: Int
) : ReleaseList