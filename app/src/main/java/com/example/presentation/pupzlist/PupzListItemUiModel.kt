package com.example.presentation.pupzlist

sealed class PupzListItemUiModel {
    data class Breed(val name: String) : PupzListItemUiModel()
    data class Subbreed(val name: String) : PupzListItemUiModel()
}