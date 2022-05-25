package de.skash.movielist.feature.movie.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.model.Movie
import de.skash.movielist.core.repository.MovieRepository
import de.skash.movielist.core.util.FilterType
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
class PopularMoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val moviePagingDataSubject: PublishSubject<PagingData<Movie>> = PublishSubject.create()
    private val moviePagingDataStream: Observable<PagingData<Movie>> = moviePagingDataSubject.hide()
    private val _moviePagingDataLiveData = MutableLiveData<PagingData<Movie>>()
    val moviePagingDataLiveData: LiveData<PagingData<Movie>> get() = _moviePagingDataLiveData

    private val movieClickSubject: PublishSubject<Int> = PublishSubject.create()
    private val movieClickStream: Observable<Int> = movieClickSubject.hide()
    private val _movieClickLiveData = SingleLiveEvent<Int>()
    val movieClickLiveData: LiveData<Int> get() = _movieClickLiveData

    private val subscriptions = CompositeDisposable()

    init {
        moviePagingDataStream
            .subscribe(_moviePagingDataLiveData::postValue)
            .addTo(subscriptions)

        movieClickStream
            .subscribe(_movieClickLiveData::postValue)
            .addTo(subscriptions)

        retrievePopularMovies()
    }

    fun onMovieClicked(movieId: Int) = movieClickSubject.onNext(movieId)

    private fun retrievePopularMovies() {
        movieRepository.fetchMoviesForFilterType(
            FilterType.Popular
        )
            .cachedIn(viewModelScope)
            .subscribeBy(
                onNext = {
                    moviePagingDataSubject.onNext(it)
                },
                onError = {
                    //TODO: Error Handling
                    Log.d(javaClass.name, "Failed to retrieve popular movies :: ", it)
                }
            ).addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}