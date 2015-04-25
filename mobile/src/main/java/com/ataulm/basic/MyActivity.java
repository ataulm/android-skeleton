package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RecyclerView listview = (RecyclerView) findViewById(R.id.listview);
        listview.setLayoutManager(new GridLayoutManager(this, 2));
        listview.setAdapter(new DummyRecyclerAdapter(getLayoutInflater()));
    }

    private static class DummyRecyclerAdapter extends RecyclerView.Adapter {

        private static final int COUNT = 25;

        private final LayoutInflater layoutInflater;

        DummyRecyclerAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.dummy_item, parent, false);
            return new DummyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return COUNT;
        }

        static class DummyViewHolder extends RecyclerView.ViewHolder {

            public DummyViewHolder(View itemView) {
                super(itemView);
            }

        }

    }

}
