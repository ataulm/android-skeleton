package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

class MyViewHolder extends RecyclerView.ViewHolder {

    private final Listener listener;

    public MyViewHolder(View itemView, Listener listener) {
        super(itemView);
        this.listener = listener;
    }

    public static MyViewHolder createFrom(ViewGroup parent, Listener listener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my, parent, false);
        return new MyViewHolder(view, listener);
    }

    public void bind(final Item item) {
        ((TextView) itemView.findViewById(R.id.title)).setText("item " + item.id);

        ImageView heart = (ImageView) itemView.findViewById(R.id.fave);
        heart.setImageResource(item.isFaved ? R.drawable.heart_fill : R.drawable.heart_empty);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(item);
            }
        });
    }

    public interface Listener {

        void onClick(Item item);

    }

}
