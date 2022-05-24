package de.skash.movielist.core.repository

import androidx.paging.PagingData
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.model.Movie
import de.skash.movielist.core.util.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun fetchMoviesForFilterType(type: Movie.FilterType): Observable<PagingData<Movie>>
    fun fetchDetailedMovieForId(id: Int): Observable<Result<DetailedMovie>>
}