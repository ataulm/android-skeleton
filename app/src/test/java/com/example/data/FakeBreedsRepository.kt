package com.example.data

import com.example.domain.Breed
import com.example.domain.BreedsRepository
import com.example.domain.Image
import com.example.domain.Subbreed
import io.reactivex.Single

internal class FakeBreedsRepository : BreedsRepository {

    var getBreeds = StubsBreedsRepository.getBreeds()
    var getBreedImages = StubsBreedsRepository.getBreedImages(Breed("", "", emptyList()))
    var getSubbreedImages = StubsBreedsRepository.getSubbreedImages(Breed("", "", emptyList()), Subbreed("", ""))

    override fun getBreeds(): Single<List<Breed>> = getBreeds
    override fun getBreedImages(breed: Breed): Single<List<Image>> = getBreedImages
    override fun getSubbreedImages(breed: Breed, subbreed: Subbreed): Single<List<Image>> = getSubbreedImages
}

private object StubsBreedsRepository : BreedsRepository {

    override fun getBreeds(): Single<List<Breed>> {
        return Single.just(emptyList())
    }

    override fun getBreedImages(breed: Breed): Single<List<Image>> {
        return Single.just(emptyList())
    }

    override fun getSubbreedImages(breed: Breed, subbreed: Subbreed): Single<List<Image>> {
        return Single.just(emptyList())
    }
}
