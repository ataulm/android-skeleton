package com.example.data

import com.squareup.moshi.Json

internal data class ApiImages(
        @Json(name = "message") val message: List<String>,
        @Json(name = "status") val status: String
)