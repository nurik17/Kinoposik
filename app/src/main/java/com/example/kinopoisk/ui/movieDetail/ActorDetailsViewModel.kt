package com.example.kinopoisk.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.PersonDetailsDto
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(private val repository: MovieListRepository) : ViewModel() {

    private val _actorInfo = MutableStateFlow<PersonDetailsDto?>(null)
    val actorInfo = _actorInfo

    private val _filteredList = MutableStateFlow<List<Movie>>(emptyList())
    val filteredList = _filteredList


    fun getPersonInfo(id : Int){
        viewModelScope.launch {
            val person = repository.getPersonDetails(id)
            val listFilms = person.films.sortedByDescending {
                it.rating?.toDouble()
            }
                .map {
                    it.filmId
                }
                .distinct() //delete dublicates
                .take(15)
            _actorInfo.value = person

            val list = mutableListOf<Movie>()
            for(filmId in listFilms){
                val movie = repository.getMovieDetails(filmId)
                list.add(movie)
                val filteredList = list.filter {
                    !it.genres!!.any { it.genre!!.contains("ток-шоу") }
                }
                _filteredList.value = filteredList
            }
        }
    }
}