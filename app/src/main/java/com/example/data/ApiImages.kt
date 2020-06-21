package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ApiImages(
        @Json(name = "message") val message: List<String>,
        @Json(name = "status") val status: String
)