package com.example.presentation.images

import com.example.presentation.EventHandler

sealed class ImagesUiModel {
    data class Data(val title: String, val items: List<ImageItemUiModel>) : ImagesUiModel()
    data class Error(val message: String, val onClickRetry: EventHandler<Unit>?) : ImagesUiModel()
    object Loading : ImagesUiModel()
}

data class ImageItemUiModel(val url: String, val onClick: EventHandler<Unit>)
