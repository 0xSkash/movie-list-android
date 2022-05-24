package de.skash.movielist.feature.tv.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.movielist.core.model.DetailedTvShow
import de.skash.movielist.core.repository.TvShowRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DetailedTvShowViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val tvShowSubject: PublishSubject<DetailedTvShow> = PublishSubject.create()
    private val tvShowStream: Observable<DetailedTvShow> = tvShowSubject.hide()
    private val _tvShowLiveData = MutableLiveData<DetailedTvShow>()
    val tvShowLiveData: LiveData<DetailedTvShow> get() = _tvShowLiveData

    private val subscriptions = CompositeDisposable()

    init {
        tvShowStream
            .subscribe(_tvShowLiveData::postValue)
            .addTo(subscriptions)
    }

    fun fetchDetailedTvShowForId(id: Int) {
        tvShowRepository.fetchDetailedTvShow(id)
            .subscribeBy(
                onSuccess = {
                    tvShowSubject.onNext(it)
                },
                onError = {
                    //TODO: Error Handling
                    Log.d(javaClass.name, "Failed to fetch details for TV Show with id: $id :: ", it)
                }
            ).addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}