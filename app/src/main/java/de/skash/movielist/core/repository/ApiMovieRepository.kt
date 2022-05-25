package de.skash.movielist.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.model.Movie
import de.skash.movielist.core.network.api.MovieApi
import de.skash.movielist.core.util.ErrorType
import de.skash.movielist.core.util.FilterType
import de.skash.movielist.core.util.Result
import de.skash.movielist.core.util.getErrorType
import de.skash.movielist.core.util.paging.MoviePagingSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ApiMovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override fun fetchMoviesForFilterType(type: FilterType): Observable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, type)
            }).observable
    }

    override fun fetchDetailedMovieForId(id: Int): Observable<Result<DetailedMovie>> {
        return Observable.just(Result.Loading<DetailedMovie>())
            .subscribeOn(Schedulers.io())
            .flatMap {
                movieApi.getMovieDetailed(id)
            }
            .map<Result<DetailedMovie>> {
                Result.Success(DetailedMovie(it))
            }
            .onErrorReturn {
                Result.Error(it.getErrorType() ?: ErrorType.MovieNotFound)
            }
    }

    override fun fetchRecommendationsForMovie(movieId: Int): Observable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, FilterType.Recommendations(movieId))
            }).observable
    }
}