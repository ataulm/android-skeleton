package com.novoda.androidskeleton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity implements OnFakeNewsItemClickListener {

    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RecyclerView rvA = (RecyclerView) findViewById(R.id.column_a);
        rvA.setLayoutManager(new GridLayoutManager(this, 1));
        rvA.setAdapter(new FakeNewsAdapter("a", this));

        RecyclerView rvB = (RecyclerView) findViewById(R.id.column_b);
        rvB.setLayoutManager(new GridLayoutManager(this, 1));
        rvB.setAdapter(new FakeNewsAdapter("b", this));

        RecyclerView rvC = (RecyclerView) findViewById(R.id.column_c);
        rvC.setLayoutManager(new GridLayoutManager(this, 1));
        rvC.setAdapter(new FakeNewsAdapter("c", this));
    }

    @Override
    public void onClick(String itemText) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, itemText, Toast.LENGTH_SHORT);
        toast.show();
    }

    private static class FakeNewsAdapter extends RecyclerView.Adapter {

        private final String columnName;
        private final OnFakeNewsItemClickListener onFakeNewsItemClickListener;

        FakeNewsAdapter(String columnName, OnFakeNewsItemClickListener onFakeNewsItemClickListener) {
            this.columnName = columnName;
            this.onFakeNewsItemClickListener = onFakeNewsItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
            return new PlainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final String itemText = "column " + columnName + " " + position;
            ((TextView) holder.itemView).setText(itemText);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFakeNewsItemClickListener.onClick(itemText);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 8;
        }

    }

    private static class PlainViewHolder extends RecyclerView.ViewHolder {

        PlainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
