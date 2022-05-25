package de.skash.movielist.feature.movie.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.model.Movie
import de.skash.movielist.core.repository.MovieRepository
import de.skash.movielist.core.util.Result
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

    private val movieSubject: PublishSubject<Result<DetailedMovie>> = PublishSubject.create()
    private val movieStream: Observable<Result<DetailedMovie>> = movieSubject.hide()
    private val _movieLivedata = MutableLiveData<Result<DetailedMovie>>()
    val movieLivedata: LiveData<Result<DetailedMovie>> get() = _movieLivedata

    private val recommendedMoviesSubject: PublishSubject<PagingData<Movie>> = PublishSubject.create()
    private val recommendedMoviesStream: Observable<PagingData<Movie>> = recommendedMoviesSubject.hide()
    private val _recommendedMoviesLivedata = MutableLiveData<PagingData<Movie>>()
    val recommendedMoviesLivedata: LiveData<PagingData<Movie>> get() = _recommendedMoviesLivedata

    private val subscriptions = CompositeDisposable()

    init {
        movieStream
            .subscribe(_movieLivedata::postValue)
            .addTo(subscriptions)

        recommendedMoviesStream
            .subscribe(_recommendedMoviesLivedata::postValue)
            .addTo(subscriptions)
    }

    fun fetchDetailedMovieForId(id: Int) {
        movieRepository.fetchDetailedMovieForId(id)
            .subscribeBy(
                onNext = {
                    movieSubject.onNext(it)
                },
                onError = {
                    Log.d(javaClass.name, "Failed to fetch detailed movie for id: $id :: ", it)
                }
            ).addTo(subscriptions)
    }

    fun fetchRecommendationsForId(id: Int) {
        movieRepository.fetchRecommendationsForMovie(id)
            .subscribeBy(
                onNext = {
                    recommendedMoviesSubject.onNext(it)
                },
                onError = {
                    Log.d(javaClass.name, "Failed to fetch recommended movies for movie with id: $id :: ", it)
                }
            ).addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}