package com.example.presentation.pupzlist

import com.example.presentation.EventHandler

sealed class PupzListItemUiModel {
    data class Breed(val name: String, val onClick: EventHandler<Unit>) : PupzListItemUiModel()
    data class Subbreed(val name: String, val onClick:  EventHandler<Unit>) : PupzListItemUiModel()
}
