package com.example.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BreedsRepository {

    fun syncBreeds(): Completable
    fun getBreeds(): Flowable<List<Breed>>
    fun getBreedImages(breed: Breed): Single<List<Image>>
    fun getSubbreedImages(breed: Breed, subbreed: Subbreed): Single<List<Image>>
}
