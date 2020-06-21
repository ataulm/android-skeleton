package com.example.presentation.pupzlist

import com.example.presentation.EventHandler

sealed class PupzListUiModel {
    data class Data(val items: List<PupzListItemUiModel>, val onClickFeelingLucky: EventHandler<Unit>) : PupzListUiModel()
    data class Error(val message: String, val onClickRetry: EventHandler<Unit>) : PupzListUiModel()
    object Loading : PupzListUiModel()
}

sealed class PupzListItemUiModel {
    data class Breed(val name: String, val onClick: EventHandler<Unit>) : PupzListItemUiModel()
    data class Subbreed(val name: String, val onClick: EventHandler<Unit>) : PupzListItemUiModel()
}
