package de.skash.movielist.core.repository

import androidx.paging.PagingData
import de.skash.movielist.core.model.Person
import io.reactivex.rxjava3.core.Observable

interface PeopleRepository {
    fun fetchPopularPeople(): Observable<PagingData<Person>>
}