package com.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_movie.view.*

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun inflate(parent: ViewGroup): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.view_movie, parent, false)
            return MyViewHolder(itemView)
        }
    }

    fun bind(movie: Movie) {
        itemView.movieTitleView.text = movie.title
        itemView.movieOverviewView.text = movie.overview
//        TODO bind image
    }
}
