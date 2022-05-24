package de.skash.movielist.core.repository

import androidx.paging.PagingData
import de.skash.movielist.core.model.DetailedPerson
import de.skash.movielist.core.model.Person
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PersonRepository {
    fun fetchPopularPeople(): Observable<PagingData<Person>>
    fun fetchDetailedPerson(id: Int): Single<DetailedPerson>
}