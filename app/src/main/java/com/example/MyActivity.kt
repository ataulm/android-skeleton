package com.example

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ataulm.rv.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_my.*

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
        return StubTmdbApi()
    }
}
