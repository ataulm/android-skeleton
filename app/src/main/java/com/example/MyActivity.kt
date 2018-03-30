package com.example

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.ataulm.rv.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_my.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MyActivity : AppCompatActivity() {

    private val tmdbRepository = TmdbRepository(createApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        movieListView.layoutManager = LinearLayoutManager(this)
        movieListView.adapter = MyAdapter(emptyList())
        movieListView.addItemDecoration(SpacesItemDecoration.newInstance(0, resources.getDimensionPixelSize(R.dimen.space_between_items), 1))

        tmdbRepository.topRatedMovies().observe(this, Observer<List<Movie>> { movieList ->
            if (movieList != null) {
                (movieListView.adapter as MyAdapter).update(movieList)
            }
        })
    }

    private fun createApi(): TmdbApi {
        val httpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(apiKeyInterceptor())
                .build()
        return Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build().create(TmdbApi::class.java)
    }

    private fun apiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val httpUrl = chain.request().url().newBuilder().addQueryParameter("api_key", BuildConfig.TMDB_API_KEY).build()
            chain.proceed(requestBuilder.url(httpUrl).build())
        }
    }
}
