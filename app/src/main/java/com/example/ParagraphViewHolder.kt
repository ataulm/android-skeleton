package com.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class ParagraphViewHolder(private val view: TextView) : RecyclerView.ViewHolder(view) {

    companion object {
        fun createView(parent: ViewGroup): ParagraphViewHolder {
            val textView = LayoutInflater.from(parent.context).inflate(R.layout.view_paragraph, parent, false) as TextView
            return ParagraphViewHolder(textView)
        }
    }

    fun setText(text: String) {
        view.text = text
    }
}
