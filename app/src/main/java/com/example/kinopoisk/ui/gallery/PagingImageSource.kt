package com.example.kinopoisk.ui.gallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.entity.PicturesItem
import java.lang.Exception

class PagingImageSource(
    private val id : Int?,
    private val type : String
) : PagingSource<Int,PicturesItem>() {
    private val repository = MovieListRepository(RetrofitClient.api)

    override fun getRefreshKey(state: PagingState<Int, PicturesItem>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicturesItem> {
        val pageIndex = params.key ?: 1
        return try{
            val response = repository.getPictures(id!!,type,pageIndex)
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