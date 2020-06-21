package com.example.data

import io.reactivex.Single

internal class StubsDogCeo : DogCeo {

    var breeds: Single<ApiResponse> = Single.just(ApiResponse(message = emptyMap(), status = "success"))
    var breedImages: Single<ApiImages> = Single.just(ApiImages(message = emptyList(), status = "success"))
    var subbreedImages: Single<ApiImages> = Single.just(ApiImages(message = emptyList(), status = "success"))

    override fun breeds(): Single<ApiResponse> = breeds
    override fun breedImages(breed: String): Single<ApiImages> = breedImages
    override fun subbreedImages(breed: String, subbreed: String): Single<ApiImages> = subbreedImages
}
