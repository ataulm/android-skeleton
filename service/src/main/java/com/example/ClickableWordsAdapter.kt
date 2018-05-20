package com.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_clickable_word.view.*

class ClickableWordsAdapter(private val callback: Callback) : RecyclerView.Adapter<PlainViewHolder>() {

    var list: List<ClickableWord> = emptyList()

    fun update(list: List<ClickableWord>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_clickable_word, parent, false)
        return PlainViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlainViewHolder, position: Int) {
        val clickableWord = list[position]
        holder.itemView.clickableWordTextView.text = clickableWord.word
        holder.itemView.clickableWordDeleteButton.setOnClickListener { callback.onClickDelete(clickableWord) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface Callback {

        fun onClickDelete(word: ClickableWord)
    }
}
