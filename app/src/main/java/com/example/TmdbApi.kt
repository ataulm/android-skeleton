package com.example

import io.reactivex.Observable
import retrofit2.http.GET

interface TmdbApi {

    @GET("configuration/")
    fun configuration(): Observable<Configuration>

    @GET("movie/top_rated/")
    fun topRatedMovies(): Observable<TopRatedMovies>

    data class Configuration(val baseUrl: String, val posterSizes: List<String>)
    data class TopRatedMovies(val movies: List<Movie>)
    data class Movie(val id: Int, val title: String, val overview: String, val posterPath: String)
}
