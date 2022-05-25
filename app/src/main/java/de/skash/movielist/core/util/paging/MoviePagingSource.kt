package de.skash.movielist.core.util.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import de.skash.movielist.core.model.Movie
import de.skash.movielist.core.network.api.MovieApi
import de.skash.movielist.core.util.FilterType
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val PAGING_STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val filterType: FilterType
) : RxPagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val position = params.key ?: PAGING_STARTING_PAGE_INDEX

        return when (filterType) {
            FilterType.Popular -> {
                movieApi.getPopularMovies(position, params.loadSize)
            }
            is FilterType.Recommendations -> {
                movieApi.getMovieRecommendations(filterType.movieId, position, params.loadSize)
            }
            FilterType.Trending -> {
                movieApi.getTrendingMovies(position, params.loadSize)
            }
            FilterType.Upcoming -> {
                movieApi.getUpcomingMovies(position, params.loadSize)
            }
        }
            .subscribeOn(Schedulers.io())
            .map { response ->
                LoadResult.Page(
                    data = response.results.map { Movie(it) },
                    prevKey = if (position == PAGING_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.results.isEmpty()) null else position + 1
                )
            }
    }
}