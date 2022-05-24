package de.skash.movielist.core.repository

import androidx.paging.PagingData
import de.skash.movielist.core.model.TvShow
import io.reactivex.rxjava3.core.Observable

interface TvShowRepository {
    fun fetchPopularTvShows(): Observable<PagingData<TvShow>>
}