package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MyActivity extends AppCompatActivity {

    private View appBar;
    private View toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        appBar = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(((Toolbar) toolbar));
        setTitle("What");

        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.foo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            showSearch();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (searchIsHidden()) {
            super.onBackPressed();
        } else {
            hideSearch();
        }
    }

    private boolean searchIsHidden() {
        return appBar.getY() == 0;
    }

    private void showSearch() {
        appBar.setTranslationY(-toolbar.getHeight());
    }

    private void hideSearch() {
        appBar.setTranslationY(0);
    }

}
