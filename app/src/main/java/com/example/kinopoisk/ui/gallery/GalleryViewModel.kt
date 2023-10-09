package com.example.kinopoisk.ui.gallery

import android.widget.CalendarView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.entity.PicturesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class GalleryViewModel(
    val id : Int?
) : ViewModel() {

    private val repository = MovieListRepository(RetrofitClient.api)

    var image : Flow<PagingData<PicturesItem>>? = null

    private val _images20 = MutableStateFlow<List<PicturesItem>>(emptyList())
    val images20 = _images20.asStateFlow()

    fun get20Images(type:String){
        viewModelScope.launch {
            val picture = repository.getPictures(id!!,type,1)
            _images20.value = picture
        }
    }

    fun getPictures(type : String){
        val picturesData = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PagingImageSource(id,type) }
        ).flow.cachedIn(viewModelScope)
        image = picturesData
    }
}

class GalleryViewModelFactory(
    private val id: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryViewModel(id) as T
    }
}