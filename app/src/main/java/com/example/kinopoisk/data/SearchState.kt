package com.example.kinopoisk.data

import androidx.paging.PagingData
import com.example.kinopoisk.entity.Movie

sealed class SearchState {
    object Loading : SearchState()
    data class Results(val data: PagingData<Movie>) : SearchState()
    object Empty : SearchState()
    data class Error(val error: Throwable) : SearchState()
}
