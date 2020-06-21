package com.example.domain

import io.reactivex.Flowable

class GetBreedsUsecase(private val repository: BreedsRepository) {

    /**
     * Controversial 👀 This favors up-to-date data over displaying cached data and also loading.
     * Still works offline, but no flashing data (at cost of always fetching the payload where
     * possible).
     */
    operator fun invoke(): Flowable<List<Breed>> = repository.syncBreeds()
            .onErrorComplete() // if there's a network exception (no internet), then carry on
            .andThen(repository.getBreeds()) // so we can try to load from DB if there is something
}
