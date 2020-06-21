package com.example.data

import com.example.domain.Breed
import com.example.domain.BreedsRepository
import com.example.domain.Image
import com.example.domain.Subbreed
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

internal class StubsBreedsRepository : BreedsRepository {

    var syncBreeds = Completable.complete()
    var getBreeds: Flowable<List<Breed>> = Flowable.just(emptyList())
    var getBreedImages: Single<List<Image>> = Single.just(emptyList())
    var getSubbreedImages: Single<List<Image>> = Single.just(emptyList())

    override fun syncBreeds(): Completable = syncBreeds
    override fun getBreeds(): Flowable<List<Breed>> = getBreeds
    override fun getBreedImages(breed: Breed): Single<List<Image>> = getBreedImages
    override fun getSubbreedImages(breed: Breed, subbreed: Subbreed): Single<List<Image>> = getSubbreedImages
}
