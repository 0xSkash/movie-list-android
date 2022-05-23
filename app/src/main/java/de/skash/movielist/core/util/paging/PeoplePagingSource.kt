package de.skash.movielist.core.util.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import de.skash.movielist.core.model.Person
import de.skash.movielist.core.network.api.PeopleApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val PAGING_STARTING_PAGE_INDEX = 1

class PeoplePagingSource(
    private val peopleApi: PeopleApi
) : RxPagingSource<Int, Person>() {

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Person>> {
        val position = params.key ?: PAGING_STARTING_PAGE_INDEX

        return peopleApi.getPopularPeople(position, params.loadSize)
            .subscribeOn(Schedulers.io())
            .map { response ->
                LoadResult.Page(
                    data = response.results.map { Person(it) },
                    prevKey = if (position == PAGING_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.results.isEmpty()) null else position + 1
                )
            }
    }
}