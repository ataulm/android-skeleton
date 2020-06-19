package com.example.data

import io.reactivex.Single

internal class FakeDogCeo : DogCeo {

    var breeds = StubsDogCeo.breeds()
    var breedImages = StubsDogCeo.breedImages("")
    var subbreedImages = StubsDogCeo.subbreedImages("", "")

    override fun breeds(): Single<ApiResponse> = breeds
    override fun breedImages(breed: String): Single<ApiImages> = breedImages
    override fun subbreedImages(breed: String, subbreed: String): Single<ApiImages> = subbreedImages
}

private object StubsDogCeo : DogCeo {

    override fun breeds(): Single<ApiResponse> = Single.just(
            ApiResponse(message = emptyMap(), status = "success")
    )

    override fun breedImages(breed: String): Single<ApiImages> = Single.just(
            ApiImages(message = emptyList(), status = "success")
    )

    override fun subbreedImages(breed: String, subbreed: String): Single<ApiImages> = Single.just(
            ApiImages(message = emptyList(), status = "success")
    )
}
