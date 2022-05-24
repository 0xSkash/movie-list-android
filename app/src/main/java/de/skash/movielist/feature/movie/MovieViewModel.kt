package de.skash.movielist.feature.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.skash.movielist.core.util.SingleLiveEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieViewModel : ViewModel() {

    private val movieClickSubject: PublishSubject<Int> = PublishSubject.create()
    private val movieClickStream: Observable<Int> = movieClickSubject.hide()
    private val _movieClickLiveData = SingleLiveEvent<Int>()
    val movieClickLiveData: LiveData<Int> get() = _movieClickLiveData

    private val subscriptions = CompositeDisposable()

    init {
        movieClickStream
            .subscribe(_movieClickLiveData::postValue)
            .addTo(subscriptions)
    }

    fun onMovieClicked(movieId: Int) = movieClickSubject.onNext(movieId)

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}