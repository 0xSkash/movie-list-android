package de.skash.movielist.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import de.skash.movielist.core.model.Person
import de.skash.movielist.core.network.api.PeopleApi
import de.skash.movielist.core.util.paging.PeoplePagingSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ApiPeopleRepository @Inject constructor(
    private val peopleApi: PeopleApi
) : PeopleRepository {

    override fun fetchPopularPeople(): Observable<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                PeoplePagingSource(peopleApi)
            }
        ).observable
    }
}