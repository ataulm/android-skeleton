package com.example.presentation.images

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Breed
import com.example.domain.GetImagesUsecase
import com.example.domain.Subbreed
import com.example.presentation.Event
import com.example.presentation.EventHandler
import com.example.presentation.subscribeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

internal class ImagesViewModel(getImages: GetImagesUsecase, breed: Breed, subbreed: Subbreed?) : ViewModel() {

    private val _images = MutableLiveData<List<ImageUiModel>>()
    val images: LiveData<List<ImageUiModel>>
        get() = _images

    private val _events = MutableLiveData<Event<ShowSpotlightCommand>>()
    val events: LiveData<Event<ShowSpotlightCommand>>
        get() = _events

    private val disposables = CompositeDisposable()

    init {
        val subscription = getImages(breed, subbreed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        onSuccess = Consumer { images ->
                            val uiModels = images.map { image ->
                                ImageUiModel(url = image.url, onClick = EventHandler {
                                    val showSpotlightCommand = ShowSpotlightCommand(SpotlightImageUiModel(image.url))
                                    _events.value = Event(showSpotlightCommand)
                                })
                            }
                            _images.value = uiModels
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

    data class ShowSpotlightCommand(val spotlightImageUiModel: SpotlightImageUiModel)
}
