package com.example.kinopoisk.domain

import com.example.kinopoisk.data.MovieListDto
import com.example.kinopoisk.data.PagedMoviesDto
import com.example.kinopoisk.data.PersonDetailsDto
import com.example.kinopoisk.data.PicturesDto
import com.example.kinopoisk.data.ReleaseListDto
import com.example.kinopoisk.data.SimilarsDto
import com.example.kinopoisk.data.StaffDto
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.SimilarFilms
import com.example.kinopoisk.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("month") month:String,
        @Query("year") year:String,
        @Query("page") page: Int?
    ): MovieListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopular100(
        @Query("page") page : Int?,
    ) : PagedMoviesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun getTop250(
        @Query("page") page : Int?,
    ) : PagedMoviesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.1/films/releases")
    suspend fun getReleases(
        @Query("year") year : String,
        @Query("month") month : String,
        @Query("page") page : Int?,
    ) : ReleaseListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieDetails(
        @Path("id") id : Int
    ) : Movie

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v1/staff?")
    suspend fun getStaffInfo(
        @Query("filmId") filmId : Int
    ) : StaffDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getPictures(
        @Path("id") id : Int,
        @Query("type") type : String,
        @Query("page") page : Int,
    ) : PicturesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilm(
        @Path("id") id : Int
    ) : MovieListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v1/staff/{id}")
    suspend fun getPersonDetails(
        @Path("id") id : Int
    ) : PersonDetailsDto
}
