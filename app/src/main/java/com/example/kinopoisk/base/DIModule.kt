package com.example.kinopoisk.base

import com.example.kinopoisk.domain.MovieApi
import com.example.kinopoisk.domain.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DIModule {

    @Provides
    fun provideMovieApi(retrofit: RetrofitClient) : MovieApi{
        return retrofit.api
    }
    @Provides
    fun provideRetrofitClient(): RetrofitClient{
        return RetrofitClient
    }
}
