package com.example.kinopoisk.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.feature.home.data.Movie
import com.example.kinopoisk.feature.home.repository.MovieListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieListRepository): ViewModel(){

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        loadPremieres()
    }

    private fun loadPremieres() {
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.getPremieres(2023,"JUNE")
            }.fold(
                onSuccess = { _movies.value},
                onFailure = { Log.d("HomeViewModel", it.message ?: "")}
            )
        }
    }
}
