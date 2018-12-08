package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    private val itemsAdapter = ItemsAdapter { toast(it) }
    private val numberList = IntArray(100) { it }.toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        recyclerView.adapter = itemsAdapter

        itemsAdapter.submitList(numberList)
    }

    private var toast: Toast? = null
    private fun toast(value: Int) {
        toast?.cancel()
        toast = Toast.makeText(this, "click $value", Toast.LENGTH_SHORT).apply { show() }
    }
}

