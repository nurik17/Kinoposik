package com.example.kinopoisk.ui.search

import SearchInfoPagingSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoisk.data.ParamsFilterFilm
import com.example.kinopoisk.data.SearchState
import com.example.kinopoisk.data.SettingData
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.entity.FilterCountry
import com.example.kinopoisk.entity.FilterGenre
import com.example.kinopoisk.entity.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = MovieListRepository(RetrofitClient.api)
    private lateinit var countriesList : List<FilterCountry>
    private lateinit var genresList : List<FilterGenre>


    private val _searchSettings = MutableStateFlow(SettingData())
    val searchSettings = _searchSettings.asStateFlow()

    private val _filterFlow = MutableStateFlow(ParamsFilterFilm())
    val filterFlow = _filterFlow.asStateFlow()

    fun getFiltersFull() = _filterFlow.value

    fun updateFiltersFull(filterFilm : ParamsFilterFilm){
        viewModelScope.launch {
            if(_filterFlow.value != filterFilm)
                _filterFlow.value = filterFilm
        }
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