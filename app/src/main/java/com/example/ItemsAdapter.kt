package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_number.view.*

class ItemsAdapter(val onClick: (Int) -> Unit) : ListAdapter<Int, NumberViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NumberViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.numberTextView.text = "$item number!"
        holder.itemView.setOnClickListener { onClick(item) }
    }

    object Differ : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
    }
}

class NumberViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {

        fun inflate(parent: ViewGroup): NumberViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_number, parent, false)
            return NumberViewHolder(view)
        }
    }
}
