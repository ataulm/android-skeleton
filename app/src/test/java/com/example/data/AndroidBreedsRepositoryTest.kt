package com.example.data

import com.example.RxSchedulerRule
import com.example.data.db.FakeBreedsDao
import com.example.domain.Breed
import com.example.domain.Subbreed
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import java.io.IOException

internal class AndroidBreedsRepositoryTest {

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    private val stubsDogCeo = StubsDogCeo()
    private val repository = AndroidBreedsRepository(stubsDogCeo, FakeBreedsDao())

    @Test
    fun `given network exception when syncing, repo emits network exception`() {
        val networkException = IOException()
        stubsDogCeo.breeds = Single.error(networkException)

        repository.syncBreeds().test()
                .assertError(networkException)
    }

    @Test
    fun `given non-success response, repo emits exception`() {
        stubsDogCeo.breeds = Single.just(ApiResponse(
                message = emptyMap(),
                status = "not success"
        ))

        repository.syncBreeds().test()
                .assertError(RuntimeException::class.java)
    }

    @Test
    fun `given successful response, repo emits breeds`() {
        stubsDogCeo.breeds = Single.just(ApiResponse(
                message = mapOf(
                        "breed" to listOf("subbreed1", "subbreed2"),
                        "breed2" to emptyList()
                ),
                status = "success"
        ))

        repository.syncBreeds().andThen(repository.getBreeds())
                .test()
                .assertResult(listOf(
                        Breed(id = "breed", name = "Breed", subbreeds = listOf(
                                Subbreed("subbreed1", "Subbreed1 Breed"),
                                Subbreed("subbreed2", "Subbreed2 Breed")
                        )),
                        Breed(id = "breed2", name = "Breed2", subbreeds = emptyList())
                ))
    }
}
