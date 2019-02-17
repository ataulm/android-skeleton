package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_my.*
import kotlinx.android.synthetic.main.item_view.view.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val adapter = SelectableViewAdapter()
        recyclerView.adapter = adapter

        val items = IntArray(10) { it }.map { Item("Item ${it + 1}", it == 0) }
        adapter.submitList(items)
    }
}

class SelectableViewAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.titleTextView.text = item.title
        holder.itemView.selectionStatusView.visibility = if (item.activated) View.VISIBLE else View.INVISIBLE
    }
}

object ItemDiffer : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.title == newItem.title
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
data class Item(val title: String, val activated: Boolean)
