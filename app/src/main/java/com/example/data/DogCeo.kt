package com.example.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

// by breed: https://dog.ceo/api/breed/hound/images
internal interface DogCeo {

    @GET("breeds/list/all")
    fun breeds(): Single<ApiResponse>

    @GET("breed/{breed}/images")
    fun breedImages(@Path("breed") breed: String): Single<ApiImages>

    @GET("breed/{breed}/{subbreed}/images")
    fun subbreedImages(
            @Path("breed") breed: String,
            @Path("subbreed") subbreed: String
    ): Single<ApiImages>
}