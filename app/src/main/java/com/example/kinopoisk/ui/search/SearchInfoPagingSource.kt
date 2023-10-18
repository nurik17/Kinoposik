import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.domain.MovieListRepository
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.entity.Movie
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1

class SearchInfoPagingSource(
    private val countries: Int?,
    private val genres: Int?,
    private val order: String,
    private val type: String,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val yearFrom: Int,
    private val yearTo: Int,
    private val keyword: String
) : PagingSource<Int, Movie>(){
    private val repository = MovieListRepository(RetrofitClient.api)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        try {
            val response = repository.getSearchInfo(
                countries,
                genres,
                order,
                type,
                ratingFrom,
                ratingTo,
                yearFrom,
                yearTo,
                keyword,
                pageIndex
            )
            return LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if(response.isEmpty()) null else pageIndex + 1
            )
        }catch (e : Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1

}