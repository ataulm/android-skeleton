package com.example.data

import com.example.domain.Breed
import com.example.domain.BreedsRepository
import io.reactivex.Single

internal class FakeBreedsRepository : BreedsRepository {

    var getBreeds = StubsBreedsRepository.getBreeds()

    override fun getBreeds(): Single<List<Breed>> = getBreeds
}

private object StubsBreedsRepository : BreedsRepository {

    override fun getBreeds(): Single<List<Breed>> {
        return Single.just(emptyList())
    }
}
