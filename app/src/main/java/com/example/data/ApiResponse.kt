package com.example.data

import com.squareup.moshi.Json

internal data class ApiResponse(
        @Json(name = "message") val message: Map<String, List<String>>,
        @Json(name = "status") val status: String
)
