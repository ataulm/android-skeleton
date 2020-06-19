package com.example.data

import com.example.domain.Breed
import com.example.domain.Subbreed
import io.reactivex.Single
import org.junit.Test
import java.io.IOException

class BreedsRepositoryTest {

    private val fakeDogCeo = FakeDogCeo()
    private val repository = BreedsRepository(fakeDogCeo)

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
                        Breed(name = "breed", subbreeds = listOf(
                                Subbreed("subbreed1"),
                                Subbreed("subbreed2")
                        )),
                        Breed(name = "breed2", subbreeds = emptyList())
                ))
    }
}
