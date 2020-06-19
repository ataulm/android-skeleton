package com.example.domain

import io.reactivex.Single

interface BreedsRepository {

    fun getBreeds(): Single<List<Breed>>
}
