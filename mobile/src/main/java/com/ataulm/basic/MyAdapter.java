package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final List<Item> items;
    private final MyViewHolder.Listener listener;

    public MyAdapter(List<Item> items, MyViewHolder.Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MyViewHolder.createFrom(parent, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
