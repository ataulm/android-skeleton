package com.example.presentation.pupzlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.R

internal class PupzListAdapter : ListAdapter<PupzListItemUiModel, RecyclerView.ViewHolder>(Differ) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PupzListItemUiModel.Breed -> R.layout.pupz_list_item_breed
            is PupzListItemUiModel.Subbreed -> R.layout.pupz_list_item_subbreed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return DoNothingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PupzListItemUiModel.Breed -> holder.itemView.bind(item)
            is PupzListItemUiModel.Subbreed -> holder.itemView.bind(item)
        }
    }

    private object Differ : DiffUtil.ItemCallback<PupzListItemUiModel>() {

        override fun areItemsTheSame(oldItem: PupzListItemUiModel, newItem: PupzListItemUiModel): Boolean {
            return if (oldItem is PupzListItemUiModel.Breed && newItem is PupzListItemUiModel.Breed) {
                oldItem.name == newItem.name
            } else if (oldItem is PupzListItemUiModel.Subbreed && newItem is PupzListItemUiModel.Subbreed) {
                oldItem.name == newItem.name
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItem: PupzListItemUiModel, newItem: PupzListItemUiModel): Boolean {
            return oldItem == newItem
        }
    }
}

private class DoNothingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

private fun View.bind(breed: PupzListItemUiModel.Breed) {
    (this as TextView).text = breed.name
    setOnClickListener {
        // TODO: bind to clicks
    }
}

private fun View.bind(subbreed: PupzListItemUiModel.Subbreed) {
    (this as TextView).text = subbreed.name
    setOnClickListener {
        // TODO: bind to clicks
    }
}
