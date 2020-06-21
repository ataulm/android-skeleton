package com.example.presentation.pupzlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Breed
import com.example.domain.GetBreedsUsecase
import com.example.domain.Subbreed
import com.example.presentation.Event
import com.example.presentation.EventHandler
import com.example.presentation.RxResult
import com.example.presentation.toRxResultWithInitialDelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
        val disposable = getBreeds().toRxResultWithInitialDelay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _state.value = when (it) {
                        is RxResult.Success -> {
                            if (it.value.isEmpty()) {
                                PupzListUiModel.Error(
                                        message = "ugh, empty. Try again?",
                                        onClickRetry = EventHandler { TODO("retry handler") }
                                )
                            } else {
                                val items: List<PupzListItemUiModel> = it.value.toItemUiModels()
                                PupzListUiModel.Data(
                                        items = items,
                                        onClickFeelingLucky = items.clickRandomItem()
                                )
                            }
                        }
                        is RxResult.Error -> {
                            PupzListUiModel.Error(
                                    message = "Something went wrong. Try again?",
                                    onClickRetry = EventHandler { TODO("retry handler") }
                            )
                        }
                        RxResult.InFlight -> {
                            PupzListUiModel.Loading
                        }
                    }
                }
        disposables.add(disposable)
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
