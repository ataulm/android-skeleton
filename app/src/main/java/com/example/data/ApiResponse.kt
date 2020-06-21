package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ApiResponse(
        @Json(name = "message") val message: Map<String, List<String>>,
        @Json(name = "status") val status: String
)
