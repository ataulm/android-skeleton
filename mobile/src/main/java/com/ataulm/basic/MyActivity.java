package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyActivity extends AppCompatActivity implements ItemClickListener {

    private ToastDisplayer toastDisplayer;
    private ItemAdapter itemAdapter;
    private List<Item> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        toastDisplayer = new ToastDisplayer(this);

        RecyclerView listview = (RecyclerView) findViewById(R.id.listview);
        listview.setItemAnimator(null);

        listview.setLayoutManager(new LinearLayoutManager(this));
        items = createItems(20);

        itemAdapter = new ItemAdapter(items, this);
        listview.setAdapter(itemAdapter);
    }

    private static List<Item> createItems(int count) {
        List<Item> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(new Item(i, false));
        }
        Collections.sort(items);
        return items;
    }

    @Override
    public void onClick(Item item) {
        toastDisplayer.display("onClick " + item.id);
    }

    @Override
    public void onClickSelect(Item item) {
        if (item.selected) {
            toastDisplayer.display("item already selected");
            return;
        }

        updateAdapterWithItemChanged(item, true);
    }

    @Override
    public void onClickDeselect(Item item) {
        if (!item.selected) {
            toastDisplayer.display("item already deselected");
            return;
        }
        updateAdapterWithItemChanged(item, false);
    }

    private void updateAdapterWithItemChanged(Item itemToChange, boolean selected) {
        List<Item> copy = new ArrayList<>(items.size());
        for (Item item : items) {
            if (item.id == itemToChange.id) {
                copy.add(new Item(item.id, selected));
            } else {
                copy.add(item);
            }
        }

        Collections.sort(copy);

        items = copy;
        itemAdapter.update(items, itemToChange);
    }
}
