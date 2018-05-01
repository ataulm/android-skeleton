package com.example

class AndroidFilmsRepository : FilmsRepository {

    override fun load(callback: FilmsRepository.Callback) {
        // ... fetch films on bg thread
        // callback.onNext(films) on main thread
    }
}

interface FilmsRepository {

    fun load(callback: Callback)

    interface Callback {

        fun onNext(films: List<Film>)
    }
}
