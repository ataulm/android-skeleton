package com.example.data

import com.squareup.moshi.Json

data class ApiImages(
        @Json(name = "message") val message: List<String>,
        @Json(name = "status") val status: String
)