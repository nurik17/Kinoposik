package com.example.kinopoisk.feature.home.retrofit

import android.graphics.Movie
import com.example.kinopoisk.feature.home.data.MovieList
import com.example.kinopoisk.feature.home.retrofit.MovieApi.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/premieres")

    suspend fun movies(@Query("year") year:Int, @Query("month") month:String): MovieList

    companion object{
        const val BASE_URL = "https://kinopoiskapiunofficial.tech"
        private const val API_KEY = "9cbb66dc-4c6d-47c1-8489-ca7c09a7bef3"
    }
}
val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(MovieApi::class.java)
