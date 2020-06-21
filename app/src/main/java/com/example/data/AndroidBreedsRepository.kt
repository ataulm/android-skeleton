package com.example.data

import android.annotation.SuppressLint
import com.example.data.db.BreedsDao
import com.example.data.db.DbBreed
import com.example.data.db.DbSubbreed
import com.example.domain.Breed
import com.example.domain.BreedsRepository
import com.example.domain.Image
import com.example.domain.Subbreed
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

internal class AndroidBreedsRepository(
        private val dogCeo: DogCeo,
        private val breedsDao: BreedsDao
) : BreedsRepository {

    override fun syncBreeds(): Completable {
        return fetchBreedsFromNetwork()
                .flatMapCompletable { persistBreedsToDb(it) }
    }

    private fun fetchBreedsFromNetwork(): Single<Map<String, List<String>>> {
        return dogCeo.breeds().flatMap {
            if (it.status == "success") {
                Single.defer { Single.just(it.message) }
            } else {
                Single.defer { Single.error<Map<String, List<String>>?>(RuntimeException("Something went wrong fetching breeds from network.")) }
            }
        }
    }

    private fun persistBreedsToDb(breeds: Map<String, List<String>>): Completable {
        val dbBreeds = mutableListOf<DbBreed>()
        val dbSubbreeds = mutableListOf<DbSubbreed>()
        breeds.entries.map { entry ->
            dbBreeds.add(DbBreed(name = entry.key))
            entry.value.forEach { subbreed ->
                val dbSubbreed = DbSubbreed(
                        id = "${entry.key}_${subbreed}",
                        name = subbreed,
                        breedName = entry.key
                )
                dbSubbreeds.add(dbSubbreed)
            }
        }
        return Completable.mergeArray(breedsDao.insertBreeds(dbBreeds), breedsDao.insertSubbreeds(dbSubbreeds))
    }

    override fun getBreeds(): Flowable<List<Breed>> {
        return Flowable.combineLatest(
                breedsDao.queryBreeds(),
                breedsDao.querySubbreeds(),
                BiFunction { breeds, subbreeds ->
                    val subbreedsByBreed = subbreeds.groupBy { it.breedName }
                    breeds.map { dbBreed ->
                        Breed(
                                id = dbBreed.name,
                                name = dbBreed.name.capitalizeWords(),
                                subbreeds = subbreedsByBreed[dbBreed.name]
                                        ?.map { dbSubbreed ->
                                            Subbreed(
                                                    id = dbSubbreed.name,
                                                    name = "${dbSubbreed.name} ${dbBreed.name}".capitalizeWords()
                                            )
                                        }
                                        ?: emptyList()
                        )
                    }
                }
        )
    }

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
