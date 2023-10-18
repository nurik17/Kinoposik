package com.example.kinopoisk.ui.search

import SearchInfoPagingSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoisk.data.SettingData
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.entity.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {

    private val repository = MovieListRepository(RetrofitClient.api)

    private var _searchSettings = MutableStateFlow(SettingData())
    val searchSettings = _searchSettings.asStateFlow()

    fun changeCountry(selectedCountry: Int?) {
        _searchSettings.value.selectedCountry = selectedCountry
    }

    fun changeGenre(selectedGenre: Int?) {
        _searchSettings.value.selectedGenre = selectedGenre
    }

    fun changeOrder(selectedOrder: String) {
        _searchSettings.value.selectedOrder = selectedOrder
    }

    fun changeType(type: String) {
        _searchSettings.value.selectedType = type
    }

    fun changeYearFrom(yearFrom: Int) {
        _searchSettings.value.yearFrom = yearFrom
    }

    fun changeYearTo(yearTo: Int) {
        _searchSettings.value.yearTo = yearTo
    }

    fun changeRatingFrom(ratingFrom: Int) {
        _searchSettings.value.ratingFrom = ratingFrom
    }

    fun changeRatingTo(ratingTo: Int) {
        _searchSettings.value.ratingTo = ratingTo
    }


    fun getMovies(keyword: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchInfoPagingSource(
                    countries = searchSettings.value.selectedCountry,
                    genres = searchSettings.value.selectedGenre,
                    order = searchSettings.value.selectedOrder,
                    keyword = keyword,
                    yearFrom = searchSettings.value.yearFrom,
                    yearTo = searchSettings.value.yearTo,
                    ratingFrom = searchSettings.value.ratingFrom,
                    ratingTo = searchSettings.value.ratingTo,
                    type = searchSettings.value.selectedType
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

}