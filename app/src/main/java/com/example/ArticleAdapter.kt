package com.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ArticleAdapter(private val article: List<String>) : RecyclerView.Adapter<ParagraphViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphViewHolder {
        return ParagraphViewHolder.createView(parent)
    }

    override fun getItemCount(): Int {
        return article.size
    }

    override fun onBindViewHolder(viewHolder: ParagraphViewHolder, index: Int) {
        val paragraph = article[index]
        viewHolder.setText(paragraph)
    }
}
