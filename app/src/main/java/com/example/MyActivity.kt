package com.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    private val films = filmsInitial().toMutableList()
    private val filmsAdapter = FilmsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        filmsAdapter.submitList(films.toUiModel())
        recyclerView.adapter = filmsAdapter
    }

    private fun List<Film>.toUiModel(): List<FilmUiModel> = map { film ->
        FilmUiModel(
                posterUrl = film.posterUrl,
                title = film.title,
                watched = film.watched,
                onClickWatch = {
                    val storedFilm = films.find { it.title == film.title }!!
                    val index = films.indexOf(storedFilm)
                    films[index] = storedFilm.copy(watched = !storedFilm.watched)
                    filmsAdapter.submitList(films.toUiModel())
                }
        )
    }
}

private fun filmsInitial(): List<Film> = listOf(
        Film(title = "The Shawshank Redemption"),
        Film(title = "The Godfather"),
        Film(title = "The Dark Knight"),
        Film(title = "The Godfather: Part II"),
        Film(title = "The Lord of the Rings: The Return of the King"),
        Film(title = "Pulp Fiction"),
        Film(title = "Schindler’s List"),
        Film(title = "12 Angry Men"),
        Film(title = "Inception"),
        Film(title = "Fight Club")
)

data class Film(
        val title: String,
        val posterUrl: String = "https://robohash.org/$title.png",
        val watched: Boolean = false
)

data class FilmUiModel(
        val posterUrl: String,
        val title: String,
        val watched: Boolean,
        val onClickWatch: () -> Unit
)
