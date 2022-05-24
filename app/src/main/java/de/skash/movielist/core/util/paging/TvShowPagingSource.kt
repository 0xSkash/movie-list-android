package de.skash.movielist.core.util.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import de.skash.movielist.core.model.TvShow
import de.skash.movielist.core.network.api.TvShowApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val PAGING_STARTING_PAGE_INDEX = 1

class TvShowPagingSource(
    private val tvShowApi: TvShowApi
) : RxPagingSource<Int, TvShow>() {

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TvShow>> {
        val position = params.key ?: PAGING_STARTING_PAGE_INDEX

        return tvShowApi.getPopularTvShows(position, params.loadSize)
            .subscribeOn(Schedulers.io())
            .map { response ->
                LoadResult.Page(
                    data = response.results.map { TvShow(it) },
                    prevKey = if (position == PAGING_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.results.isEmpty()) null else position + 1
                )
            }
    }
}
