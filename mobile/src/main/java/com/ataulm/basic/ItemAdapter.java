package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;
    private final MyActivity.ItemClickListener itemClickListener;

    ItemAdapter(List<Item> items, MyActivity.ItemClickListener itemClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return ViewHolder.inflate(parent, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Item item = items.get(i);
        viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView moreActionOneTextView;
        private final TextView moreActionTwoTextView;
        private final MyActivity.ItemClickListener itemClickListener;

        static ViewHolder inflate(ViewGroup parent, MyActivity.ItemClickListener itemClickListener) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(layoutInflater.inflate(R.layout.dummy_item, parent, false), itemClickListener);
        }

        public ViewHolder(View itemView, MyActivity.ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            this.titleTextView = (TextView) itemView.findViewById(R.id.text_title);
            this.moreActionOneTextView = (TextView) itemView.findViewById(R.id.text_more_action_one);
            this.moreActionTwoTextView = (TextView) itemView.findViewById(R.id.text_more_action_two);
        }

        public void bind(final Item item) {
            final String text = "Item " + item.id;
            titleTextView.setText(text);

            itemView.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClick(item);
                        }

                    });

            moreActionOneTextView.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClickSelect(item);
                        }

                    });

            moreActionTwoTextView.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClickDeselect(item);
                        }

                    });
        }
    }

}
