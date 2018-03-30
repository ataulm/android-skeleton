package com.example

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class TmdbRepository(private val tmdbApi: TmdbApi) {

    fun topRatedMovies(): LiveData<List<Movie>> {
        return TopRatedLiveData(tmdbApi)
    }

    private class TopRatedLiveData(private val tmdbApi: TmdbApi) : MutableLiveData<List<Movie>>() {

        private var disposable: Disposable? = null

        override fun onActive() {
            disposable = Observable.combineLatest(tmdbApi.configuration(), tmdbApi.topRatedMovies(), toMovieList())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { value = it }
        }

        private fun toMovieList(): BiFunction<TmdbApi.Configuration, TmdbApi.TopRatedMovies, List<Movie>> {
            return BiFunction { configuration, topRatedMovies ->
                topRatedMovies.movies.map {
                    // dumb selection of poster size, API seems to order by size, so this'll be the biggest
                    // probably this could return a map<size:urls>, then choose which closer to the view
                    val posterUrl = configuration.baseUrl + configuration.posterSizes.last() + it.posterPath
                    Movie(it.id, it.title, it.overview, posterUrl)
                }
            }
        }

        override fun onInactive() {
            disposable?.dispose()
        }
    }
}
