package com.example.presentation.images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.R
import com.example.presentation.DoNothingViewHolder

internal class ImagesAdapter : ListAdapter<ImageUiModel, RecyclerView.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.images_item, parent, false)
        return DoNothingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.bind(item)
    }

    private object Differ : DiffUtil.ItemCallback<ImageUiModel>() {

        override fun areItemsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean {
            return oldItem == newItem
        }
    }
}

private fun View.bind(image: ImageUiModel) {
    Glide.with(this).load(image.url).into(this as ImageView)
}
