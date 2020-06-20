package com.example.presentation.pupzlist

import com.example.presentation.EventHandler

data class PupzListUiModel(
        val onClickFeelingLucky: EventHandler<Unit>,
        val items: List<PupzListItemUiModel>
)

sealed class PupzListItemUiModel {
    data class Breed(val name: String, val onClick: EventHandler<Unit>) : PupzListItemUiModel()
    data class Subbreed(val name: String, val onClick:  EventHandler<Unit>) : PupzListItemUiModel()
}
