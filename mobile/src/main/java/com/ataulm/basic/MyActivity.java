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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        master = (RecyclerView) findViewById(R.id.master);
        master.setLayoutManager(new LinearLayoutManager(this));
        master.setAdapter(new AlphabetAdapter());

        detail = (TextView) findViewById(R.id.detail);
    }

    private static class AlphabetAdapter extends RecyclerView.Adapter<AlphabetViewHolder> {

        private int positionActivated;

        @Override
        public AlphabetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return AlphabetViewHolder.inflateWithParamsFrom(parent);
        }

        @Override
        public void onBindViewHolder(AlphabetViewHolder holder, int position) {
            Alphabet alphabet = Alphabet.values()[position];
            ((TextView) holder.itemView).setText(alphabet.title());
        }

        @Override
        public int getItemCount() {
            return Alphabet.values().length;
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
