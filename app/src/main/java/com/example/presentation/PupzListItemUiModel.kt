package com.example.presentation

sealed class PupzListItemUiModel {
    data class Breed(val name: String) : PupzListItemUiModel()
    data class Subbreed(val name: String) : PupzListItemUiModel()
}