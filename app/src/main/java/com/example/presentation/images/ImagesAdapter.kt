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

internal class ImagesAdapter : ListAdapter<ImageItemUiModel, RecyclerView.ViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.images_item, parent, false)
        return DoNothingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.bind(item)
    }

    private object Differ : DiffUtil.ItemCallback<ImageItemUiModel>() {

        override fun areItemsTheSame(oldItem: ImageItemUiModel, newItem: ImageItemUiModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ImageItemUiModel, newItem: ImageItemUiModel): Boolean {
            return oldItem == newItem
        }
    }
}

private fun View.bind(imageItem: ImageItemUiModel) {
    setOnClickListener { imageItem.onClick.handler(Unit) }
    Glide.with(this).load(imageItem.url).into(this as ImageView)
}
