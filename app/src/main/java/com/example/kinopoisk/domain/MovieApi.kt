package com.example.kinopoisk.domain

import com.example.kinopoisk.data.MovieListDto
import com.example.kinopoisk.data.PagedMoviesDto
import com.example.kinopoisk.data.ReleaseListDto
import com.example.kinopoisk.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("month") month:String,
        @Query("year") year:String
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
}
