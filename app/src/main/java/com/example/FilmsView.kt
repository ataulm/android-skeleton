package com.example

import android.support.v7.widget.RecyclerView

class AndroidFilmsView(private val recyclerView: RecyclerView) : FilmsView {

    private lateinit var presenter: FilmsPresenter

    override fun setPresenter(presenter: FilmsPresenter) {
        this.presenter = presenter
    }

    override fun display(films: List<Film>) {
        // display films in recyclerView
    }
}

interface FilmsView {

    fun setPresenter(presenter: FilmsPresenter)

    fun display(films: List<Film>)
}
