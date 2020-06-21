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
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

internal class PupzListViewModel(getBreeds: GetBreedsUsecase) : ViewModel() {

    private val _state = MutableLiveData<PupzListUiModel>()
    val state: LiveData<PupzListUiModel>
        get() = _state

    private val _events = MutableLiveData<Event<NavigateCommand>>()
    val events: LiveData<Event<NavigateCommand>>
        get() = _events

    private val disposables = CompositeDisposable()

    init {
        val subscription = getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        onNext = Consumer { breeds ->
                            val items: List<PupzListItemUiModel> = breeds.toItemUiModels()
                            _state.value = PupzListUiModel(
                                    items = items,
                                    onClickFeelingLucky = items.clickRandomItem()
                            )
                            // TODO: if breeds is empty, we could display the dog with a message!
                        },
                        onError = Consumer<Throwable> {
                            // TODO: show error, allow retry
                            Log.d("!!!", it.message)
                        }
                )
        disposables.add(subscription)
    }

    private fun List<Breed>.toItemUiModels() = flatMap { breed ->
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

    // not a good approach for analytics - can't differentiate between item clicks and die clicks
    private fun List<PupzListItemUiModel>.clickRandomItem() = EventHandler<Unit> {
        when (val randomItem = this[Random.nextInt(size)]) {
            is PupzListItemUiModel.Breed -> randomItem.onClick.handler(Unit)
            is PupzListItemUiModel.Subbreed -> randomItem.onClick.handler(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    data class NavigateCommand(val breed: Breed, val subbreed: Subbreed? = null)
}
