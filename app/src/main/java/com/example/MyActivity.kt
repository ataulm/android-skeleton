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

    private val items = IntArray(10) { it }.map {
        ItemUiModel("ItemUiModel ${it + 1}", it == 0)
    }.toMutableList()

    private val onSelect: (ItemUiModel) -> Unit = { selectedItem: ItemUiModel ->
        val previouslySelectedItem = items.first { it.activated }
        items[items.indexOf(previouslySelectedItem)] = previouslySelectedItem.copy(activated = false)
        items[items.indexOf(selectedItem)] = selectedItem.copy(activated = true)
        adapter.submitList(items.toList())
    }

    private val adapter = SelectableViewAdapter(onSelect)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        adapter.submitList(items.toList())
        recyclerView.adapter = adapter
    }
}

class SelectableViewAdapter(private val onSelect: (ItemUiModel) -> Unit) : ListAdapter<ItemUiModel, RecyclerView.ViewHolder>(ItemDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.titleTextView.text = item.title
        holder.itemView.selectionStatusView.visibility = if (item.activated) View.VISIBLE else View.INVISIBLE
        holder.itemView.setOnClickListener { onSelect(item) }
    }
}

object ItemDiffer : DiffUtil.ItemCallback<ItemUiModel>() {
    override fun areItemsTheSame(oldItem: ItemUiModel, newItem: ItemUiModel) = oldItem.title == newItem.title
    override fun areContentsTheSame(oldItem: ItemUiModel, newItem: ItemUiModel) = oldItem == newItem
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
data class ItemUiModel(val title: String, val activated: Boolean)
