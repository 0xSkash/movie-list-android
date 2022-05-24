package de.skash.movielist.feature.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.Person
import de.skash.movielist.core.repository.PersonRepository
import de.skash.movielist.core.util.SingleLiveEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PersonRepository
) : ViewModel() {

    private val peoplePagingDataSubject: PublishSubject<PagingData<Person>> =
        PublishSubject.create()
    private val peoplePagingDataStream: Observable<PagingData<Person>> =
        peoplePagingDataSubject.hide()
    private val _peoplePagingDataLiveData = MutableLiveData<PagingData<Person>>()
    val peoplePagingDataLiveData: LiveData<PagingData<Person>> get() = _peoplePagingDataLiveData

    private val personClickSubject: PublishSubject<Int> = PublishSubject.create()
    private val personClickStream: Observable<Int> = personClickSubject.hide()
    private val _personClickLiveData = SingleLiveEvent<Int>()
    val personClickLiveData: LiveData<Int> get() = _personClickLiveData

    private val subscriptions = CompositeDisposable()

    init {
        peoplePagingDataStream
            .subscribe(_peoplePagingDataLiveData::postValue)
            .addTo(subscriptions)

        personClickStream
            .subscribe(_personClickLiveData::postValue)
            .addTo(subscriptions)

        fetchPopularPeople()
    }

    fun onPersonClicked(personId: Int) = personClickSubject.onNext(personId)

    private fun fetchPopularPeople() {
        peopleRepository.fetchPopularPeople()
            .cachedIn(viewModelScope)
            .subscribeBy(
                onNext = {
                    peoplePagingDataSubject.onNext(it)
                },
                onError = {
                    //TODO: Error Handling
                    Log.d(javaClass.name, "Failed to retrieve popular people :: ", it)
                }
            ).addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}