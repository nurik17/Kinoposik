package com.example.kinopoisk.domain

import com.example.kinopoisk.data.MovieSearchDto
import com.example.kinopoisk.data.PersonDetailsDto
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.PicturesItem
import com.example.kinopoisk.entity.SimilarsItem
import com.example.kinopoisk.entity.StaffItem
import retrofit2.http.Query

class MovieListRepository(private val api: MovieApi) {

    suspend fun getPremieres(month: String, year: String, page: Int?): List<Movie> {
        return api.getPremieres(month, year, page).items
    }

    suspend fun getPopular100(page: Int?): List<Movie> {
        return api.getPopular100(page).films
    }

    suspend fun getTop250(page: Int?): List<Movie> {
        return api.getTop250(page).films
    }

    suspend fun getReleases(year: String, month: String, page: Int?): List<Movie> {
        return api.getReleases(year, month, page).releases
    }

    suspend fun getMovieDetails(id: Int): Movie {
        return api.getMovieDetails(id)
    }

    suspend fun getStaffInfo(filmId: Int): ArrayList<StaffItem> {
        return api.getStaffInfo(filmId)
    }

    suspend fun getPictures(id: Int, type: String, page: Int): List<PicturesItem> {
        return api.getPictures(id, type, page).items
    }

    suspend fun getSimilarFilm(id: Int): List<Movie> {
        return api.getSimilarFilm(id).items
    }

    suspend fun getPersonDetails(id: Int): PersonDetailsDto {
        return api.getPersonDetails(id)
    }

    suspend fun getSearchInfo(
        countries: Int?,
        genres: Int?,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        imdbld: String,
        page: Int?,
    ): List<Movie> {
        return api.searchInfo(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            imdbld,
            page
        ).items
    }
}
