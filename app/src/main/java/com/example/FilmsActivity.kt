package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_films.*

class FilmsActivity : AppCompatActivity() {

    private lateinit var presenter: FilmsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        val view = AndroidFilmsView(recyclerView)
        presenter = FilmsPresenter(AndroidFilmsRepository(), view)
        view.setPresenter(presenter)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        presenter.unsubscribe()
        super.onPause()
    }
}
