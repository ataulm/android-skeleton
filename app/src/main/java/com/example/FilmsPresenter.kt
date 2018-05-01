package com.example

class FilmsPresenter(private val repository: FilmsRepository, private val view: FilmsView) {

    fun subscribe() {
        repository.load(object : FilmsRepository.Callback {
            override fun onNext(films: List<Film>) {
                view.display(films)
            }
        })
    }

    fun unsubscribe() {
        // cancel any loads in progress/callbacks
    }
}
