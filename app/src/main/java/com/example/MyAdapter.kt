package com.example

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MyAdapter(private var list: List<Movie>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    fun update(list: List<Movie>) {
        this.list = list
        // TODO: swap out for the new  ListAdapter
        notifyDataSetChanged()
    }
}
