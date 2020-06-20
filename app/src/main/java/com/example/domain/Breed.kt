package com.example.domain

import java.io.Serializable

data class Breed(
        val name: String,
        val subbreeds: List<Subbreed>
): Serializable
