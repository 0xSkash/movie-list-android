package de.skash.movielist.feature.people.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.DetailedPerson
import de.skash.movielist.core.repository.PersonRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DetailedPersonViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {

    private val personSubject: PublishSubject<DetailedPerson> = PublishSubject.create()
    private val personStream: Observable<DetailedPerson> = personSubject.hide()
    private val _personLiveData = MutableLiveData<DetailedPerson>()
    val personLiveData: LiveData<DetailedPerson> get() = _personLiveData

    private val subscriptions = CompositeDisposable()

    init {
        personStream
            .subscribe(_personLiveData::postValue)
            .addTo(subscriptions)
    }

    fun fetchDetailedPersonForId(id: Int) {
        personRepository.fetchDetailedPerson(id)
            .subscribeBy(
                onSuccess = {
                    personSubject.onNext(it)
                },
                onError = {
                    //TODO: Error Handling
                    Log.d(javaClass.name, "Failed to fetch person with id $id :: ", it)
                }
            ).addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}