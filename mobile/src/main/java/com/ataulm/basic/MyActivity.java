package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RecyclerView listview = (RecyclerView) findViewById(R.id.listview);
        final int spanCount = 3;
        final SpacesItemDecoration.SpanSizeLookup spanSizeLookup = new SpacesItemDecoration.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }

            @Override
            public int getSpanCount() {
                return spanCount;
            }
        };

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        layoutManager.setSpanSizeLookup(
                new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                }
        );
        listview.setLayoutManager(layoutManager);

        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        listview.addItemDecoration(new SpacesItemDecoration(spacing, spacing, spanSizeLookup));
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
            ((ItemView) holder.itemView).update(position);
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
