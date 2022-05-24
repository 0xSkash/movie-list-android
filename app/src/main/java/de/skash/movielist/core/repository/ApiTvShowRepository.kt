package de.skash.movielist.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import de.skash.movielist.core.model.TvShow
import de.skash.movielist.core.network.api.TvShowApi
import de.skash.movielist.core.util.paging.TvShowPagingSource
import io.reactivex.rxjava3.core.Observable
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
}