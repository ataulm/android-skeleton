package com.example.data

import com.example.domain.Breed
import com.example.domain.BreedsRepository
import com.example.domain.Subbreed
import io.reactivex.Single

internal class AndroidBreedsRepository(private val dogCeo: DogCeo) : BreedsRepository {

    override fun getBreeds(): Single<List<Breed>> {
        return dogCeo.breeds().flatMap {
            if (it.status == "success") {
                val breeds = it.message.entries.map { apiBreed -> apiBreed.toDomainBreed() }
                Single.just(breeds)
            } else {
                Single.error(RuntimeException("Something went wrong fetching breeds from network."))
            }
        }
    }

    private fun Map.Entry<String, List<String>>.toDomainBreed() = Breed(
            name = key,
            subbreeds = value.map { name -> Subbreed(name) }
    )
}
