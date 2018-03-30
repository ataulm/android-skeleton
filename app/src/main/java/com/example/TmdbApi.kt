package com.example

import io.reactivex.Observable
import retrofit2.http.GET

interface TmdbApi {

    @GET("configuration")
    fun configuration(): Observable<Configuration>

    @GET("movie/top_rated")
    fun topRatedMovies(): Observable<TopRatedMovies>

    data class Configuration(val images: Images)
    data class TopRatedMovies(val results: List<Movie>)
    data class Images(val secure_base_url: String, val poster_sizes: List<String>)
    data class Movie(val id: Int, val title: String, val overview: String, val poster_path: String)
}
