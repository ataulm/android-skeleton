package com.example.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Breed
import com.example.domain.GetBreedsUsecase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

internal class PupzListViewModel(getBreeds: GetBreedsUsecase) : ViewModel() {

    private val _breeds = MutableLiveData<List<Breed>>()
    val breeds: LiveData<List<Breed>>
        get() = _breeds

    private val disposables = CompositeDisposable()

    init {
        val subscription = getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        onSuccess = Consumer { _breeds.value = it },
                        onError = Consumer<Throwable> {
                            Log.d("!!!", it.message)
                        }
                )
        disposables.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

// allows named args
private fun <T> Single<T>.subscribeWith(onSuccess: Consumer<T>, onError: Consumer<Throwable>): Disposable {
    return subscribe(onSuccess, onError)
}
