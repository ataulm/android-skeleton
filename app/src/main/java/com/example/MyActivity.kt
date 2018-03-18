package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.view.doOnLayout
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        overlay.doOnLayout { translateOverlayOffScreen() }
        open_overlay_button.setOnClickListener({
            overlay.translationX = 0.toFloat()
            open_overlay_button.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        })
        close_overlay_button.setOnClickListener({
            translateOverlayOffScreen()
            open_overlay_button.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_AUTO
        })

        vertical_list.layoutManager = LinearLayoutManager(this)
        vertical_list.adapter = VerticalListAdapter()
    }

    private fun translateOverlayOffScreen() {
        overlay.translationX = overlay.width.toFloat()
    }

    private class VerticalListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return HorizontalListViewHolder.inflateHorizontalListView(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val row = holder.itemView as RecyclerView
            row.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL, false)
            row.adapter = RowAdapter()
        }

        override fun getItemCount(): Int {
            return 3
        }
    }

    private class HorizontalListViewHolder(itemView: RecyclerView) : RecyclerView.ViewHolder(itemView) {

        companion object {
            fun inflateHorizontalListView(parent: ViewGroup): HorizontalListViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_list, parent, false)
                return HorizontalListViewHolder(view as RecyclerView)
            }
        }
    }

    private class RowAdapter : RecyclerView.Adapter<SingleItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleItemViewHolder {
            return SingleItemViewHolder.inflateSingleItemView(parent)
        }

        override fun onBindViewHolder(holder: SingleItemViewHolder, position: Int) {
            (holder.itemView as TextView).text = "item $position"
            holder.itemView.setOnClickListener({ Toast.makeText(holder.itemView.context, "click $position", Toast.LENGTH_SHORT).show()})
        }

        override fun getItemCount(): Int {
            return 5
        }
    }

    private class SingleItemViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView) {

        companion object {
            fun inflateSingleItemView(parent: ViewGroup): SingleItemViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_single, parent, false)
                return SingleItemViewHolder(view as TextView)
            }
        }
    }
}

fun View.recursivelySetNotImportant() {
    setTag(R.id.important_for_accessibility, importantForAccessibility)
    importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
    if (this is ViewGroup) {
        val viewGroup = this
        for (i in 0 until viewGroup.childCount) {
            viewGroup.getChildAt(i).recursivelySetNotImportant()
        }
    }
}

fun View.recursivelyResetImportanceOrAuto() {
    val tag = getTag(R.id.important_for_accessibility)
    importantForAccessibility = if (tag != null) tag as Int else View.IMPORTANT_FOR_ACCESSIBILITY_AUTO

    if (this is ViewGroup) {
        val viewGroup = this
        for (i in 0 until viewGroup.childCount) {
            viewGroup.getChildAt(i).recursivelyResetImportanceOrAuto()
        }
    }
}
