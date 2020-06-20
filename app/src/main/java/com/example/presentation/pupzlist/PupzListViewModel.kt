package com.example.presentation.pupzlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Breed
import com.example.domain.GetBreedsUsecase
import com.example.domain.Subbreed
import com.example.presentation.Event
import com.example.presentation.EventHandler
import com.example.presentation.subscribeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

internal class PupzListViewModel(getBreeds: GetBreedsUsecase) : ViewModel() {

    private val _breeds = MutableLiveData<List<PupzListItemUiModel>>()
    val breeds: LiveData<List<PupzListItemUiModel>>
        get() = _breeds

    private val _events = MutableLiveData<Event<NavigateCommand>>()
    val events: LiveData<Event<NavigateCommand>>
        get() = _events

    private val disposables = CompositeDisposable()

    init {
        val subscription = getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        onSuccess = Consumer {
                            val uiModels: List<PupzListItemUiModel> = it.flatMap { breed ->
                                val breedUiModel = PupzListItemUiModel.Breed(breed.name, EventHandler {
                                    _events.value = Event(NavigateCommand(breed))
                                })
                                mutableListOf<PupzListItemUiModel>(breedUiModel).apply {
                                    val subbreedUiModels = breed.subbreeds.map { subbreed ->
                                        PupzListItemUiModel.Subbreed(subbreed.name, EventHandler {
                                            _events.value = Event(NavigateCommand(breed, subbreed))
                                        })
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

    data class NavigateCommand(val breed: Breed, val subbreed: Subbreed? = null)
}
