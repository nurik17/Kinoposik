package com.example.kinopoisk.data

import com.example.kinopoisk.entity.Pictures
import com.example.kinopoisk.entity.PicturesItem

class PicturesDto(
    override val items: List<PicturesItem>,
    override val total: Int,
    override val totalPages: Int
) : Pictures
