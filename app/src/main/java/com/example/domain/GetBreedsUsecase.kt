package com.example.domain

import io.reactivex.Single

class GetBreedsUsecase(private val repository: BreedsRepository) {

    operator fun invoke(): Single<List<Breed>> = repository.getBreeds()
}
