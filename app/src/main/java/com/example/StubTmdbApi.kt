package com.example

import io.reactivex.Observable

class StubTmdbApi : TmdbApi {

    override fun configuration(): Observable<TmdbApi.Configuration> {
        val configuration = TmdbApi.Configuration("https://image.tmdb.org/t/p/", posterSizes())
        return Observable.just(configuration)
    }

    private fun posterSizes(): List<String> {
        return listOf(
                "w92",
                "w154",
                "w185",
                "w342",
                "w500",
                "w780",
                "original"
        )
    }

    override fun topRatedMovies(): Observable<TmdbApi.TopRatedMovies> {
        val topRatedMovies = TmdbApi.TopRatedMovies(listOf(movie(), movie(), movie()))
        return Observable.just(topRatedMovies)
    }

    private fun movie(): TmdbApi.Movie {
        return TmdbApi.Movie(
                id = 13,
                title = "Forrest Gump",
                overview = "A man with a low IQ has accomplished great things in his life and been present during significant historic events - in each case, far exceeding what anyone imagined he could do. Yet, despite all the things he has attained, his one true love eludes him. 'Forrest Gump' is the story of a man who rose above his challenges, and who proved that determination, courage, and love are more important than ability.",
                posterPath = "/yE5d3BUhE8hCnkMUJOo1QDoOGNz.jpg"
        )
    }
}
