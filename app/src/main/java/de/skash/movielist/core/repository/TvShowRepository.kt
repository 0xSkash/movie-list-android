package de.skash.movielist.core.repository

import androidx.paging.PagingData
import de.skash.movielist.core.model.DetailedPerson
import de.skash.movielist.core.model.DetailedTvShow
import de.skash.movielist.core.model.TvShow
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TvShowRepository {
    fun fetchPopularTvShows(): Observable<PagingData<TvShow>>
    fun fetchDetailedTvShow(id: Int): Single<DetailedTvShow>
}