package com.example.kinopoisk.ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.MovieListRepository
import com.example.kinopoisk.data.State
import com.example.kinopoisk.entity.Movie
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

    private var _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    init {
         viewModelScope.launch {
             val listOfLists = mutableListOf<List<Movie>>()
             _state.value = State.Loading
             try{
                 val premieres = repository.getPremieres(currentMonth,currentYear)
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
