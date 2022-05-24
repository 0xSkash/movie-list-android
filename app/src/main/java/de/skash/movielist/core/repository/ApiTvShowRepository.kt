package de.skash.movielist.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import de.skash.movielist.core.model.DetailedPerson
import de.skash.movielist.core.model.DetailedTvShow
import de.skash.movielist.core.model.TvShow
import de.skash.movielist.core.network.api.TvShowApi
import de.skash.movielist.core.util.paging.TvShowPagingSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ApiTvShowRepository @Inject constructor(
    private val tvShowApi: TvShowApi
) : TvShowRepository {

    override fun fetchPopularTvShows(): Observable<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                TvShowPagingSource(tvShowApi)
            }
        ).observable
    }

    override fun fetchDetailedTvShow(id: Int): Single<DetailedTvShow> {
        return tvShowApi.getTvShowDetailed(id)
            .subscribeOn(Schedulers.io())
            .map {
                DetailedTvShow(it)
            }
    }
}