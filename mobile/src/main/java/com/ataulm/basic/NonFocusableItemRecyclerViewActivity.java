package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ataulm.rv.SpacesItemDecoration;

/**
 * Demonstrates how switch access (autoscan/press next) fails on scrolling lists (RecyclerView with LinearLayout manager).
 * 2015 IO Accessibility video, someone asked about infinitely scrolling lists like in FB, and the guy said it works.
 * Need to check FB with switch access but this activity doesn't work, and neither does the settings activity in lollipop.
 */
public class NonFocusableItemRecyclerViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_focusable_item_recycler_view);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        RecyclerView.Adapter adapter = new Examples(getLayoutInflater());
        adapter.setHasStableIds(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(SpacesItemDecoration.newInstance(8, 8, 1));
        list.setAdapter(adapter);
    }

    private static class Examples extends RecyclerView.Adapter<Examples.LetterViewHolder> {

        private final LayoutInflater layoutInflater;
        private Toast toast;

        Examples(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        public LetterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.view_non_focusable_item, parent, false);
            TextView label = (TextView) view.findViewById(R.id.non_focusable_item_text);
            ImageView favouriteView = (ImageView) view.findViewById(R.id.non_focusable_item_star);
            return new LetterViewHolder(view, label, favouriteView);
        }

        @Override
        public void onBindViewHolder(LetterViewHolder holder, int position) {
            final Character item = getItem(position);
            holder.label.setText(item.getTelephony());
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(v.getContext(), "clicked: " + item.name(), Toast.LENGTH_SHORT);
                    toast.show();
                }

            });
            holder.favouriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(v.getContext(), "fave: " + item.name(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Character.values().length;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).ordinal();
        }

        private Character getItem(int position) {
            return Character.values()[position];
        }

        static class LetterViewHolder extends RecyclerView.ViewHolder {

            final TextView label;
            final ImageView favouriteView;

            public LetterViewHolder(View itemView, TextView label, ImageView favouriteView) {
                super(itemView);
                this.label = label;
                this.favouriteView = favouriteView;
            }

        }

    }

}
