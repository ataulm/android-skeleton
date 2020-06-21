package com.example.data.db

import io.reactivex.Completable
import io.reactivex.Flowable

internal class FakeBreedsDao : BreedsDao {

    private var breeds: List<DbBreed> = emptyList()
    private var subbreeds: List<DbSubbreed> = emptyList()

    override fun insertBreeds(breeds: List<DbBreed>): Completable {
        this.breeds = breeds
        return Completable.defer { Completable.complete() }
    }

    override fun insertSubbreeds(subbreeds: List<DbSubbreed>): Completable {
        this.subbreeds = subbreeds
        return Completable.defer { Completable.complete() }
    }

    override fun queryBreeds(): Flowable<List<DbBreed>> {
        return Flowable.defer { Flowable.just(breeds) }
    }

    override fun querySubbreeds(): Flowable<List<DbSubbreed>> {
        return Flowable.defer { Flowable.just(subbreeds) }
    }
}
