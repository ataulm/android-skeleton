package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity implements MyViewHolder.Listener {

    private static final int COUNT = 60;

    private final List<Item> items = new ArrayList<>(COUNT);
    private MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initList();

        myAdapter = new MyAdapter(items, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(myAdapter);
    }

    private void initList() {
        for (int i = 0; i < COUNT; i++) {
            items.add(new Item(i, false));
        }
    }

    @Override
    public void onClick(Item item) {
        int position = items.indexOf(item);
        boolean isFaved = items.get(position).isFaved;

        items.set(position, new Item(position, !isFaved)); // bad person

        myAdapter.notifyItemChanged(position);
        myAdapter.notit
    }

}
