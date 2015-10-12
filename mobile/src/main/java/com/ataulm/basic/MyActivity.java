package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyActivity extends Activity {

    private RecyclerView master;
    private TextView detail;
    private AlphabetAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        master = (RecyclerView) findViewById(R.id.master);
        master.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlphabetAdapter(new OnActivatedListener() {
            @Override
            public void onActivated(int position) {
                adapter.setActivated(position);
                detail.setText(Alphabet.values()[position].description());
            }
        });
        master.setAdapter(adapter);

        detail = (TextView) findViewById(R.id.detail);
    }

    public interface OnActivatedListener {

        void onActivated(int position);

    }

    private static class AlphabetAdapter extends RecyclerView.Adapter<AlphabetViewHolder> {

        private final OnActivatedListener listener;

        private int positionActivated;

        AlphabetAdapter(OnActivatedListener listener) {
            this.listener = listener;
        }

        @Override
        public AlphabetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return AlphabetViewHolder.inflateWithParamsFrom(parent);
        }

        @Override
        public void onBindViewHolder(AlphabetViewHolder holder, final int position) {
            Alphabet alphabet = Alphabet.values()[position];
            ((TextView) holder.itemView).setText(alphabet.title());
            holder.itemView.setActivated(position == positionActivated);
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onActivated(position);
                }

            });
        }

        @Override
        public int getItemCount() {
            return Alphabet.values().length;
        }

        public void setActivated(int positionActivated) {
            int oldPositionActivated = this.positionActivated;
            this.positionActivated = positionActivated;

            notifyItemChanged(oldPositionActivated);
            notifyItemChanged(positionActivated);
        }
    }

    private enum Alphabet {

        A,
        B,
        C,
        D,
        E,
        F;

        public String title() {
            return name();
        }

        public String description() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                builder.append(name());
            }
            return builder.toString();
        }

    }

}
