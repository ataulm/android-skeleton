package com.example

import retrofit2.http.GET

interface TmdbApi {

    @GET("configuration/")
    fun configuration(): Configuration

    @GET("movie/top_rated/")
    fun topRatedMovies(): TopRatedMovies

    data class Configuration(val baseUrl: String, val posterSizes: List<String>)
    data class TopRatedMovies(val movies: List<Movie>)
    data class Movie(val id: Int, val title: String, val overview: String, val posterPath: String)
}
