package com.example

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class FilmsPresenterTest {

    private val view = mock(FilmsView::class.java)
    private val repository = SuccessfulLoadStubFilmsRepository(staticFilmList())
    private val presenter = FilmsPresenter(repository, view)

    @Test
    fun displaysFilmsFromRepository() {
        presenter.subscribe()

        verify(view).display(staticFilmList())
    }

    private fun staticFilmList(): List<Film> {
        return listOf(
            Film("1", "The Incredibles", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/huGDgQRB24IzkJLNqe5zq1pdvEE.jpg"),
            Film("2", "Whiplash", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg"),
            Film("3", "Toy Story 3", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mMltbSxwEdNE4Cv8QYLpzkHWTDo.jpg")
        )
    }

    class SuccessfulLoadStubFilmsRepository(private val films: List<Film>) : FilmsRepository {

        override fun load(callback: FilmsRepository.Callback) {
            callback.onNext(films)
        }
    }
}
