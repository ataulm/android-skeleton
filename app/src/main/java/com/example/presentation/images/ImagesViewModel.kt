package com.example.presentation.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Breed
import com.example.domain.GetImagesUsecase
import com.example.domain.Image
import com.example.domain.Subbreed
import com.example.presentation.Event
import com.example.presentation.EventHandler
import com.example.presentation.RxResult
import com.example.presentation.toRxResultWithInitialDelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

internal class ImagesViewModel(
        getImages: GetImagesUsecase,
        private val breed: Breed,
        private val subbreed: Subbreed?
) : ViewModel() {

    private val _images = MutableLiveData<ImagesUiModel>()
    val images: LiveData<ImagesUiModel>
        get() = _images

    private val _events = MutableLiveData<Event<ShowSpotlightCommand>>()
    val events: LiveData<Event<ShowSpotlightCommand>>
        get() = _events

    private val refreshRelay = PublishRelay.create<Unit>()
    private val disposables = CompositeDisposable()

    init {
        val disposable = refreshRelay.startWith(Unit).toFlowable(BackpressureStrategy.LATEST)
                .switchMap {
                    getImages(breed, subbreed)
                            .toFlowable()
                            .toRxResultWithInitialDelay()
                            .subscribeOn(Schedulers.io())
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _images.value = when (it) {
                        is RxResult.Success -> {
                            if (it.value.isEmpty()) {
                                ImagesUiModel.Error(
                                        message = "wow, no images for this pup!",
                                        // we didn't implement caching for this screen, so there's
                                        // legit no images, therefore nothing to retry.
                                        onClickRetry = null
                                )
                            } else {
                                createDataUiModel(images = it.value)
                            }
                        }
                        is RxResult.Error -> ImagesUiModel.Error(
                                message = "Something went wrong. Try again?",
                                onClickRetry = EventHandler { refreshRelay.accept(Unit) }
                        )
                        RxResult.InFlight -> ImagesUiModel.Loading
                    }
                }
        disposables.add(disposable)
    }

    private fun createDataUiModel(images: List<Image>): ImagesUiModel.Data {
        val imageItems = images.map { image ->
            ImageItemUiModel(url = image.url, onClick = EventHandler {
                val showSpotlightCommand = ShowSpotlightCommand(SpotlightImageUiModel(image.url))
                _events.value = Event(showSpotlightCommand)
            })
        }
        return ImagesUiModel.Data(items = imageItems, title = subbreed?.name ?: breed.name)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    data class ShowSpotlightCommand(val spotlightImageUiModel: SpotlightImageUiModel)
}
