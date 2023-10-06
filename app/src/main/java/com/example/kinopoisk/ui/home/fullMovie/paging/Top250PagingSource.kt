package com.example.kinopoisk.ui.home.fullMovie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.data.MovieListRepository
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.entity.Movie
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1


class Top250PagingSource : PagingSource<Int,Movie>() {
    private val repository = MovieListRepository(RetrofitClient.api)
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try{
            val response = repository.getTop250(pageIndex)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if(response.isEmpty()) null else pageIndex + 1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}