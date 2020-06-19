package com.example.presentation

import android.app.Application
import com.example.data.AndroidBreedsRepository
import com.example.data.DogCeo
import com.example.domain.BreedsRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

internal class PupzApplication : Application() {

    private val breedsRepository: BreedsRepository by lazy {
        val dogCeo = Retrofit.Builder()
                .client(OkHttpClient.Builder().build())
                .baseUrl("https://dog.ceo/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(DogCeo::class.java)
        AndroidBreedsRepository(dogCeo)
    }

    // for something scaleable, I'd usually rely on Koin or Dagger. I don't think it's worth setting
    // up for a 2-screen app though. See `github.com/ataulm/muvi` and `github.com/ataulm/whats-next`
    fun provideRepository() = breedsRepository
}
