package com.example.domain

import io.reactivex.Single

interface BreedsRepository {

    fun getBreeds(): Single<List<Breed>>
    fun getBreedImages(breed: Breed): Single<List<Image>>
    fun getSubbreedImages(breed: Breed, subbreed: Subbreed): Single<List<Image>>
}
