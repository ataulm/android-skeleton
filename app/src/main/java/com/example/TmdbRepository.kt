package com.example

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class TmdbRepository() {

    // TODO: pass the API in from above. until we get the thread switching inside the repo, we can't accept any old api - need to ensure it's safe on main thread, i.e. stub api only
    private val tmdbApi = StubTmdbApi()

    fun topRatedMovies(): LiveData<List<Movie>> {
        val mutableLiveData = MutableLiveData<List<Movie>>()
        mutableLiveData.value = toMoviesList(tmdbApi.topRatedMovies(), tmdbApi.configuration())
        return mutableLiveData
    }

    private fun toMoviesList(topRatedMovies: TmdbApi.TopRatedMovies, configuration: TmdbApi.Configuration): List<Movie> {
        return topRatedMovies.movies.map {
            // dumb selection of poster size, API seems to order by size, so this'll be the biggest
            // probably this could return a map<size:urls>, then choose which closer to the view
            val posterUrl = configuration.baseUrl + configuration.posterSizes.last() + it.posterPath
            Movie(it.id, it.title, it.overview, posterUrl)
        }
    }
}
