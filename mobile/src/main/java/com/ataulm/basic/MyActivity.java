package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        final ToastDisplayer toastDisplayer = new ToastDisplayer(this);
        listview.setAdapter(new ItemAdapter(items, new ItemClickListener() {
                                @Override
                                public void onClick(Item item) {
                                    toastDisplayer.display("onClick " + item.id);
                                }

                                @Override
                                public void onClickSelect(Item item) {
                                    toastDisplayer.display("onClick select " + item.id);
                                }

                                @Override
                                public void onClickDeselect(Item item) {
                                    toastDisplayer.display("onClick deselect " + item.id);
                                }
                            })
        );
    }

    private List<Item> createItems(int count) {
        List<Item> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(new Item(i, false));
        }
        return items;
    }

    interface ItemClickListener {
        void onClick(Item item);
        void onClickSelect(Item item);
        void onClickDeselect(Item item);
    }

}
