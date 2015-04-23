package com.ataulm.basic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RecyclerView listview = (RecyclerView) findViewById(R.id.listview);
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(new DummyAdapter(new ToastDisplayer(this)));
    }

    private static class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.ViewHolder> {

        private static final int COUNT = 25;

        private final ToastDisplayer toastDisplayer;

        DummyAdapter(ToastDisplayer toastDisplayer) {
            this.toastDisplayer = toastDisplayer;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            return ViewHolder.inflate(parent, toastDisplayer);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int backgroundRes = i % 2 == 0 ? R.drawable.item_evens_background : R.drawable.item_odds_background;
            viewHolder.bind("Item " + i, backgroundRes);
        }

        @Override
        public int getItemCount() {
            return COUNT;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView titleTextView;
            private final TextView moreActionOneTextView;
            private final TextView moreActionTwoTextView;
            private final ToastDisplayer toastDisplayer;

            static ViewHolder inflate(ViewGroup parent, ToastDisplayer toastDisplayer) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                return new ViewHolder(layoutInflater.inflate(R.layout.dummy_item, parent, false), toastDisplayer);
            }

            public ViewHolder(View itemView, ToastDisplayer toastDisplayer) {
                super(itemView);
                this.toastDisplayer = toastDisplayer;

                this.titleTextView = (TextView) itemView.findViewById(R.id.text_title);
                this.moreActionOneTextView = (TextView) itemView.findViewById(R.id.text_more_action_one);
                this.moreActionTwoTextView = (TextView) itemView.findViewById(R.id.text_more_action_two);
            }

            void bind(final String text, @DrawableRes int backgroundRes) {
                itemView.setBackgroundResource(backgroundRes);
                titleTextView.setText(text);

                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        toastDisplayer.display("onClick " + text);
                    }

                });

                moreActionOneTextView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        toastDisplayer.display("onClick " + text + " - action 1");
                    }

                });

                moreActionTwoTextView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        toastDisplayer.display("onClick " + text + " - action 2");
                    }

                });
            }

        }

    }

}
