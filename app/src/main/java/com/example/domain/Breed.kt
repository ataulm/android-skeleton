package com.example.domain

import java.io.Serializable

data class Breed(
        val id: String,
        val name: String,
        val subbreeds: List<Subbreed>
): Serializable
