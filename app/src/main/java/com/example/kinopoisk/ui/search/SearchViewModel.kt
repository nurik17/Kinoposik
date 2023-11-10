package com.example.kinopoisk.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoisk.data.ParamsFilterFilm
import com.example.kinopoisk.data.SettingData
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.entity.FilterCountry
import com.example.kinopoisk.entity.FilterCountryGenre
import com.example.kinopoisk.entity.FilterGenre
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.ui.search.adapter.SearchInfoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieListRepository) :
    ViewModel() {

    private lateinit var countriesList: List<FilterCountry>
    private lateinit var genresList: List<FilterGenre>


    private val _searchSettings = MutableStateFlow(SettingData())
    val searchSettings = _searchSettings.asStateFlow()

    private val _filterFLow = MutableStateFlow(ParamsFilterFilm())
    val filterFlow = _filterFLow.asStateFlow()

    private val _filterValuesCountriesGenres =
        MutableStateFlow<List<FilterCountryGenre>>(emptyList())
    val filterValuesCountriesGenres = _filterValuesCountriesGenres.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getGenresCountries()
            countriesList = response.countries
            genresList = response.genres
        }
    }

    fun getFiltersFull() = _filterFLow.value

    fun updateFiltersFull(filterFilm: ParamsFilterFilm) {
        viewModelScope.launch {
            if (_filterFLow.value != filterFilm) _filterFLow.value = filterFilm
        }
    }

    fun setFilterValues(filterType: String) {
        when (filterType) {
            KEY_COUNTRY -> _filterValuesCountriesGenres.value = countriesList
            KEY_GENRE -> _filterValuesCountriesGenres.value = genresList
        }
    }
    fun updateFilterCountriesGenres(type: String, keyword: String) {
        _filterValuesCountriesGenres.value = when (type) {
            KEY_COUNTRY -> {
                countriesList.filter { it.name.startsWith(keyword) }
            }
            KEY_GENRE -> {
                genresList.filter { it.name.startsWith(keyword) }
            }
            else -> {
                emptyList()
            }
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

    companion object {
        private const val KEY_COUNTRY = "country"
        private const val KEY_GENRE = "genre"
    }
}