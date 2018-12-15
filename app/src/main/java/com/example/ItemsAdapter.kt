package com.example

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_number.view.*

class ItemsAdapter(val onClick: (String) -> Unit, val onClickDelete: (String) -> Unit) : ListAdapter<String, NumberViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NumberViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.numberTextView.text = item
        holder.itemView.deleteButton.setOnClickListener { onClickDelete(item) }
        holder.itemView.setOnClickListener { onClick(item) }
    }

    object Differ : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }
}

class SwipeToDeleteCallback(context: Context, private val onSwipeToDeleteAtAdapterPosition: (Int) -> Unit) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(NO_DIRECTIONS, ItemTouchHelper.END)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeToDeleteAtAdapterPosition(viewHolder.adapterPosition)
    }

    private val background = ColorDrawable(Color.RED)
    private val deleteIndicator = ContextCompat.getDrawable(context, android.R.drawable.ic_delete)!!.apply {
        setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
    }
    private val deleteIndicatorMargin = context.resources.getDimensionPixelSize(R.dimen.delete_indicator_margin)

    override fun onChildDraw(
            canvas: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
    ) {
        val view = viewHolder.itemView
        background.apply {
            setBounds(dX.toInt(), view.top, view.left, view.bottom)
            draw(canvas)
        }
        deleteIndicator.apply {
            val drawableTop = view.top + (view.bottom - view.top - deleteIndicator.intrinsicHeight) / 2
            setBounds(
                    deleteIndicatorMargin,
                    drawableTop,
                    deleteIndicatorMargin + deleteIndicator.intrinsicWidth,
                    drawableTop + deleteIndicator.intrinsicHeight
            )
            draw(canvas)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false

    companion object {

        private const val NO_DIRECTIONS = 0
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
