package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ataulm.recyclerview.SpacesItemDecoration;
import com.ataulm.recyclerview.SpanSizeLookup;

public class MyActivity extends Activity {

    private static final boolean USE_RECYCLER_VIEW = true;
    private static final int DUMMY_ITEM_COUNT = 25;
    private static final int SPAN_COUNT = 4;

    private static final SpanSizeLookup SPAN_SIZE_LOOKUP = new SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return 1;
        }

        @Override
        public int getSpanCount() {
            return SPAN_COUNT;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (USE_RECYCLER_VIEW) {
            doRecyclerView();
        } else {
            doGridView();
        }
    }

    private void doRecyclerView() {
        setContentView(R.layout.activity_my_recyclerview);
        RecyclerView listview = (RecyclerView) findViewById(R.id.listview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        layoutManager.setSpanSizeLookup(
                new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return SPAN_SIZE_LOOKUP.getSpanSize(position);
                    }
                }
        );
        listview.setLayoutManager(layoutManager);

        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        listview.addItemDecoration(new SpacesItemDecoration(spacing, spacing, SPAN_SIZE_LOOKUP));
        listview.setAdapter(new DummyRecyclerAdapter(getLayoutInflater()));
    }

    private void doGridView() {
        setContentView(R.layout.activity_my_gridview);
        GridView listview = (GridView) findViewById(R.id.listview);
        listview.setNumColumns(SPAN_COUNT);
        listview.setAdapter(new DummyGridAdapter(getLayoutInflater()));
    }

    private static class DummyGridAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;

        DummyGridAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return DUMMY_ITEM_COUNT;
        }

        @Override
        public Integer getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.dummy_item, parent, false);
            }
            ((ItemView) view).update(getItem(position));
            return view;
        }

    }

    private static class DummyRecyclerAdapter extends RecyclerView.Adapter {

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
            ((ItemView) holder.itemView).update(position);
        }

        @Override
        public int getItemCount() {
            return DUMMY_ITEM_COUNT;
        }

        static class DummyViewHolder extends RecyclerView.ViewHolder {

            public DummyViewHolder(View itemView) {
                super(itemView);
            }

        }

    }

}
