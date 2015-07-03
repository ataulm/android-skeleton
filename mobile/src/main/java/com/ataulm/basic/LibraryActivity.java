package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class LibraryActivity extends Activity implements BookInteractionsListener {

    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                new BooksAdapter(getLayoutInflater(), new DummyBookProvider(), this)
        );
    }

    @Override
    public void onClickBook(String book) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(LibraryActivity.this, "click: " + book, Toast.LENGTH_SHORT);
        toast.show();
    }

}
