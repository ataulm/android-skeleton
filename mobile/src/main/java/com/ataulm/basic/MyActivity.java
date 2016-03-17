package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RecyclerView listview = (RecyclerView) findViewById(R.id.listview);
        listview.setLayoutManager(new LinearLayoutManager(this));
        List<Item> items = createItems(20);
        listview.setAdapter(new DummyAdapter(items, new ToastDisplayer(this)));
    }

    private List<Item> createItems(int count) {
        List<Item> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(new Item(i, false));
        }
        return items;
    }

    private static class Item {
        public final int id;
        public final boolean selected;

        Item(int id, boolean selected) {
            this.id = id;
            this.selected = selected;
        }
    }

    private static class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.ViewHolder> {

        private final List<Item> items;
        private final ToastDisplayer toastDisplayer;

        DummyAdapter(List<Item> items, ToastDisplayer toastDisplayer) {
            this.items = items;
            this.toastDisplayer = toastDisplayer;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            return ViewHolder.inflate(parent, toastDisplayer);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Item item = items.get(i);
            viewHolder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
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

            public void bind(Item item) {
                final String text = "Item " + item.id;
                titleTextView.setText(text);

                itemView.setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                toastDisplayer.display("onClick " + text);
                            }

                        });

                moreActionOneTextView.setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                toastDisplayer.display("onClick " + text + " - action 1");
                            }

                        });

                moreActionTwoTextView.setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                toastDisplayer.display("onClick " + text + " - action 2");
                            }

                        });
            }
        }

    }

}
