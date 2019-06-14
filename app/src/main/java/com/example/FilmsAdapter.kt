package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_film.view.*

class FilmsAdapter : ListAdapter<FilmUiModel, FilmViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) = holder.bind(getItem(position))

    object Differ : DiffUtil.ItemCallback<FilmUiModel>() {
        override fun areItemsTheSame(oldItem: FilmUiModel, newItem: FilmUiModel) = oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: FilmUiModel, newItem: FilmUiModel): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.posterUrl == newItem.posterUrl
                    && oldItem.watched == newItem.watched
        }
    }
}

class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(filmUiModel: FilmUiModel) {
        Picasso.get().load(filmUiModel.posterUrl).into(itemView.posterImageView)
        itemView.titleTextView.text = filmUiModel.title
        itemView.watchedIconView.setImageResource(filmUiModel.watchedDrawable())
        itemView.setOnClickListener { filmUiModel.onClickWatch() }
    }

    @DrawableRes
    private fun FilmUiModel.watchedDrawable() = if (watched) {
        R.drawable.ic_watched
    } else {
        R.drawable.ic_not_watched
    }
}
