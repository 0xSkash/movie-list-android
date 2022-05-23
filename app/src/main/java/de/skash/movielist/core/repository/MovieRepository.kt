package de.skash.movielist.core.repository

import androidx.paging.PagingData
import de.skash.movielist.core.model.Movie
import io.reactivex.rxjava3.core.Observable

interface MovieRepository {
    fun fetchMoviesForFilterType(type: Movie.FilterType): Observable<PagingData<Movie>>
}