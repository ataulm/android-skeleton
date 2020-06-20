package com.example.data

import android.annotation.SuppressLint
import com.example.domain.Breed
import com.example.domain.BreedsRepository
import com.example.domain.Image
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

    /**
     * There's some formatting here which is part of the domain, rather than the presentation.
     * Since these are names (given in English by the API), they should be capitalized.
     */
    private fun Map.Entry<String, List<String>>.toDomainBreed() = Breed(
            id = key,
            name = key.capitalizeWords(),
            subbreeds = value.map { name -> Subbreed(id = name, name = "$name $key".capitalizeWords()) }
    )

    override fun getBreedImages(breed: Breed): Single<List<Image>> {
        return dogCeo.breedImages(breed.id)
                .map { it.toDomainImage() }
    }

    override fun getSubbreedImages(breed: Breed, subbreed: Subbreed): Single<List<Image>> {
        return dogCeo.subbreedImages(breed.id, subbreed.id)
                .map { it.toDomainImage() }
    }

    private fun ApiImages.toDomainImage(): List<Image> {
        return if (status == "success") {
            message.map { url -> Image(url) }
        } else {
            throw RuntimeException("Something went wrong fetching images from network.")
        }
    }
}

@SuppressLint("DefaultLocale") // the locale passing version is experimental
private fun String.capitalizeWords() = split(" ").joinToString(" ") { it.capitalize() }
