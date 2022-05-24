package de.skash.movielist.feature.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.TvShow
import de.skash.movielist.core.repository.TvShowRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val tvShowsPagingDataSubject: PublishSubject<PagingData<TvShow>> =
        PublishSubject.create()
    private val tvShowsPagingDataStream: Observable<PagingData<TvShow>> =
        tvShowsPagingDataSubject.hide()
    private val _tvShowsPagingDataLiveData = MutableLiveData<PagingData<TvShow>>()
    val tvShowsPagingDataLiveData: LiveData<PagingData<TvShow>> get() = _tvShowsPagingDataLiveData

    private val subscriptions = CompositeDisposable()

    init {
        tvShowsPagingDataStream
            .subscribe(_tvShowsPagingDataLiveData::postValue)
            .addTo(subscriptions)

        fetchPopularTvShows()
    }

    private fun fetchPopularTvShows() {
        tvShowRepository.fetchPopularTvShows()
            .cachedIn(viewModelScope)
            .subscribeBy(
                onNext = {
                    tvShowsPagingDataSubject.onNext(it)
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
