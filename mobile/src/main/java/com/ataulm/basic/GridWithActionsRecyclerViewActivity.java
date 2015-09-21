package com.ataulm.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ataulm.rv.SpacesItemDecoration;

public class GridWithActionsRecyclerViewActivity extends Activity {

    private static final int SPAN_COUNT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_with_actions_recycler_view);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        RecyclerView.Adapter adapter = new Examples(getLayoutInflater());
        adapter.setHasStableIds(true);
        list.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        list.addItemDecoration(SpacesItemDecoration.newInstance(8, 8, SPAN_COUNT));
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
            View view = layoutInflater.inflate(R.layout.view_with_child_item, parent, false);
            TextView label = (TextView) view.findViewById(R.id.label);
            ImageView favouriteView = (ImageView) view.findViewById(R.id.favourite);
            return new LetterViewHolder(view, label, favouriteView);
        }

        @Override
        public void onBindViewHolder(LetterViewHolder holder, int position) {
            final Character item = getItem(position);
            holder.label.setText(item.getTelephony());
            holder.favouriteView.setContentDescription("fave: " + item.name());
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
//            holder.itemView.setContentDescription(item.getPhonicPronunciation());
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
