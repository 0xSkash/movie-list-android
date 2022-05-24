package de.skash.movielist.feature.movie.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.repository.MovieRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DetailedMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieSubject: PublishSubject<DetailedMovie> = PublishSubject.create()
    private val movieStream: Observable<DetailedMovie> = movieSubject.hide()
    private val _movieLivedata = MutableLiveData<DetailedMovie>()
    val movieLivedata: LiveData<DetailedMovie> get() = _movieLivedata

    private val subscriptions = CompositeDisposable()

    init {
        movieStream
            .subscribe(_movieLivedata::postValue)
            .addTo(subscriptions)
    }

    fun fetchDetailedMovieForId(id: Int) {
        movieRepository.fetchDetailedMovieForId(id)
            .subscribeBy(
                onSuccess = {
                    movieSubject.onNext(it)
                },
                onError = {
                    //TODO: Error Handling
                    Log.d(javaClass.name, "Failed to fetch detailed movie for id: $id :: ", it)
                }
            ).addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}