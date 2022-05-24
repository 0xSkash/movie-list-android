package de.skash.movielist.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import de.skash.movielist.core.model.DetailedPerson
import de.skash.movielist.core.model.Person
import de.skash.movielist.core.network.api.PeopleApi
import de.skash.movielist.core.util.paging.PeoplePagingSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ApiPersonRepository @Inject constructor(
    private val peopleApi: PeopleApi
) : PersonRepository {

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

    override fun fetchDetailedPerson(id: Int): Single<DetailedPerson> {
        return peopleApi.getPersonDetailed(id)
            .subscribeOn(Schedulers.io())
            .map {
                DetailedPerson(it)
            }
    }
}