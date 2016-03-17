package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;
    private final ItemClickListener itemClickListener;

    ItemAdapter(List<Item> items, ItemClickListener itemClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
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

    @Override
    public long getItemId(int position) {
        return items.get(position).id;
    }

    public void update(List<Item> backingData) {
        this.items.clear();
        this.items.addAll(backingData);

        notifyDataSetChanged();
    }

    public void update(List<Item> backingData, Item changedItem) {
        this.items.clear();
        this.items.addAll(backingData);

        int i = backingData.indexOf(changedItem);
        notifyItemChanged(i);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView selectButton;
        private final TextView deselectButton;
        private final ItemClickListener itemClickListener;

        static ViewHolder inflate(ViewGroup parent, ItemClickListener itemClickListener) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(layoutInflater.inflate(R.layout.dummy_item, parent, false), itemClickListener);
        }

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            this.titleTextView = (TextView) itemView.findViewById(R.id.text_title);
            this.selectButton = (TextView) itemView.findViewById(R.id.text_more_action_one);
            this.deselectButton = (TextView) itemView.findViewById(R.id.text_more_action_two);
        }

        public void bind(final Item item) {
            final String text;
            if (item.selected) {
                text = "Item " + item.id + " (selected)";
            } else {
                text = "Item " + item.id;
            }

            titleTextView.setText(text);

            itemView.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClick(item);
                        }

                    });

            selectButton.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClickSelect(item);
                        }

                    });

            deselectButton.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClickDeselect(item);
                        }

                    });
        }
    }

}
