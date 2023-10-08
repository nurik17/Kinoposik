package com.example.kinopoisk.ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.data.State
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.ui.home.fullMovie.paging.Popular100PagingSource
import com.example.kinopoisk.ui.home.fullMovie.paging.ReleasePagingSource
import com.example.kinopoisk.ui.home.fullMovie.paging.Top250PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HomeViewModel(private val repository: MovieListRepository) : ViewModel() {

    private val currentDate = Date()
    private val monthFormat = SimpleDateFormat("MMMM", Locale.US)
    private val currentMonth = monthFormat.format(currentDate).uppercase(Locale.US) //month

    private val year = Date()
    private val yearFormat = SimpleDateFormat("yyyy", Locale.US)
    private val currentYear = yearFormat.format(year).uppercase(Locale.US)

    private var _genresList = MutableStateFlow<List<List<Movie>>>(emptyList())
    val genresList = _genresList.asStateFlow()

    private var _premieres = MutableStateFlow<List<Movie>>(emptyList())
    val premieres = _premieres.asStateFlow()

    private var _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()


    val getPopular100Paged : Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { Popular100PagingSource() }
    ).flow.cachedIn(viewModelScope)  // cachedIn state

    val top250Paged : Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { Top250PagingSource() }
    ).flow.cachedIn(viewModelScope)

    val getReleasePaged : Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ReleasePagingSource(currentYear,currentMonth) }
    ).flow.cachedIn(viewModelScope)

    fun getPremieres(){
        viewModelScope.launch {
            _state.value = State.Loading
            try{
                val premieres = repository.getPremieres(currentYear,currentMonth,null)
                _premieres.value = premieres
                _state.value = State.Success
            }catch (e : Exception){
                _state.value = State.Error
            }
        }
    }
    init {
         viewModelScope.launch {
             val listOfLists = mutableListOf<List<Movie>>()
             _state.value = State.Loading
             try{
                 val premieres = repository.getPremieres(currentMonth,currentYear,null)
                 val getPopular100 = repository.getPopular100(null)
                 val popular250 = repository.getTop250(null)
                 val release = repository.getReleases(currentYear,currentMonth,null)

                 listOfLists.add(premieres)
                 listOfLists.add(getPopular100)
                 listOfLists.add(popular250)
                 listOfLists.add(release)

                 _genresList.value = listOfLists
                 _state.value = State.Success
             }catch (e : Exception){
                 _state.value = State.Error
             }
         }
    }


    @Suppress("UNCHECKED_CAST")
    class HomeViewModelFactory(private val repository: MovieListRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}
