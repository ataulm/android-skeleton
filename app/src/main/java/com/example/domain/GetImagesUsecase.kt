package com.example.domain

import io.reactivex.Single

class GetImagesUsecase(private val repository: BreedsRepository) {

    operator fun invoke(breed: Breed, subbreed: Subbreed?): Single<List<Image>>{
        return if (subbreed == null) {
            repository.getBreedImages(breed)
        } else {
            repository.getSubbreedImages(breed, subbreed)
        }
    }
}
