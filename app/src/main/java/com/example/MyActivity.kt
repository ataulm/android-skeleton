package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    private val onClick: (String) -> Unit = { toast(it) }
    private val onClickDelete: (String) -> Unit = {
        list.remove(it)
        itemsAdapter.submitList(list.toList())
    }
    private val itemsAdapter = ItemsAdapter(onClick, onClickDelete)
    private val list = IntArray(100) { it }.map { "item: $it" }.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        recyclerView.adapter = itemsAdapter

        itemsAdapter.submitList(list.toList())

        ItemTouchHelper(
                SwipeToDeleteCallback { position ->
                    list.removeAt(position)
                    itemsAdapter.submitList(list.toList())
                }
        ).attachToRecyclerView(recyclerView)
    }

    private var toast: Toast? = null
    private fun toast(value: String) {
        toast?.cancel()
        toast = Toast.makeText(this, "click $value", Toast.LENGTH_SHORT).apply { show() }
    }
}

