package com.example.presentation.pupzlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.GetBreedsUsecase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

internal class PupzListViewModel(getBreeds: GetBreedsUsecase) : ViewModel() {

    private val _breeds = MutableLiveData<List<PupzListItemUiModel>>()
    val breeds: LiveData<List<PupzListItemUiModel>>
        get() = _breeds

    private val disposables = CompositeDisposable()

    init {
        val subscription = getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        onSuccess = Consumer {
                            val uiModels: List<PupzListItemUiModel> = it.flatMap { breed ->
                                val breedUiModel = PupzListItemUiModel.Breed(breed.name)
                                mutableListOf<PupzListItemUiModel>(breedUiModel).apply {
                                    val subbreedUiModels = breed.subbreeds.map { subbreed ->
                                        PupzListItemUiModel.Subbreed(subbreed.name)
                                    }
                                    addAll(subbreedUiModels)
                                }
                            }
                            _breeds.value = uiModels
                        },
                        onError = Consumer<Throwable> {
                            // TODO: show error, allow retry
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
