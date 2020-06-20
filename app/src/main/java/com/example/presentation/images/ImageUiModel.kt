package com.example.presentation.images

import com.example.presentation.EventHandler

data class ImageUiModel(val url: String, val onClick: EventHandler<Unit>)
