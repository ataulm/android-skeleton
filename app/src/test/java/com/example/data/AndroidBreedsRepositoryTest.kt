package com.example.data

import com.example.domain.Breed
import com.example.domain.Subbreed
import io.reactivex.Single
import org.junit.Test
import java.io.IOException

internal class AndroidBreedsRepositoryTest {

    private val fakeDogCeo = FakeDogCeo()
    private val repository = AndroidBreedsRepository(fakeDogCeo)

    @Test
    fun `given network exception, repo emits network exception`() {
        val networkException = IOException()
        fakeDogCeo.breeds = Single.error(networkException)

        repository.getBreeds().test()
                .assertError(networkException)
    }

    @Test
    fun `given non-success response, repo emits exception`() {
        fakeDogCeo.breeds = Single.just(ApiResponse(
                message = emptyMap(),
                status = "not success"
        ))

        repository.getBreeds().test()
                .assertError(RuntimeException::class.java)
    }

    @Test
    fun `given successful response, repo emits breeds`() {
        fakeDogCeo.breeds = Single.just(ApiResponse(
                message = mapOf(
                        "breed" to listOf("subbreed1", "subbreed2"),
                        "breed2" to emptyList()
                ),
                status = "success"
        ))

        repository.getBreeds().test()
                .assertResult(listOf(
                        Breed(name = "Breed", subbreeds = listOf(
                                Subbreed("Subbreed1 Breed"),
                                Subbreed("Subbreed2 Breed")
                        )),
                        Breed(name = "Breed2", subbreeds = emptyList())
                ))
    }
}
