package com.ataulm.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExamplesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExamplesActivity.ExamplesAdapter(new ExamplesAdapter.ViewHolder.Listener() {

            @Override
            public void onClick(Example example) {
                Intent intent = example.getIntent(ExamplesActivity.this);
                startActivity(intent);
            }

        }));
    }

    public static class ExamplesAdapter extends RecyclerView.Adapter<ExamplesAdapter.ViewHolder> {

        private final ViewHolder.Listener listener;

        public ExamplesAdapter(ViewHolder.Listener listener) {
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolder.inflate(parent, listener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Example example = Example.values()[position];
            holder.bind(example);
        }

        @Override
        public int getItemCount() {
            return Example.values().length;
        }

        static final class ViewHolder extends RecyclerView.ViewHolder {

            private final Listener listener;
            private final TextView titleTextView;
            private final TextView descriptionTextView;

            static ViewHolder inflate(ViewGroup parent, Listener listener) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.view_item_example, parent, false);
                TextView titleTextView = (TextView) view.findViewById(R.id.item_example_text_title);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.item_example_text_description);
                return new ViewHolder(view, listener, titleTextView, descriptionTextView);
            }

            private ViewHolder(View itemView, Listener listener, TextView titleTextView, TextView descriptionTextView) {
                super(itemView);
                this.listener = listener;
                this.titleTextView = titleTextView;
                this.descriptionTextView = descriptionTextView;
            }

            public void bind(final Example example) {
                titleTextView.setText(example.getTitle());
                descriptionTextView.setText(example.getDescription());
                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onClick(example);
                    }

                });
            }

            interface Listener {

                void onClick(Example example);

            }

        }

    }

}
