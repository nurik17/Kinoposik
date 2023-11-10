package com.example.kinopoisk.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.State
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.StaffItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieListRepository) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Movie?>(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _staffList = MutableStateFlow<ArrayList<StaffItem>>(ArrayList())
    val staffList = _staffList.asStateFlow()

    private val _similarList = MutableStateFlow<List<Movie>>(emptyList())
    val similarList = _similarList.asStateFlow()


    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()


    fun getAllDetails(movieId : Int){
        viewModelScope.launch {
            _state.value = State.Loading
            try{
                val description = repository.getMovieDetails(movieId)
                _movieDetail.value = description

                val staffList = repository.getStaffInfo(movieId)
                _staffList.value = staffList

                val similar = repository.getSimilarFilm(movieId)
                _similarList.value = similar

                _state.value = State.Success

            }catch (e : Exception){
                _state.value = State.Error
            }
        }
    }


}