package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ataulm.basic.search.SearchOverlay;

public class MyActivity extends AppCompatActivity {

    private SearchOverlay searchOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        onCreateSearchOverlay();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void onCreateSearchOverlay() {
        searchOverlay = ((SearchOverlay) findViewById(R.id.search_overlay));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.my_activity_search) {
            searchOverlay.setVisibility(View.VISIBLE);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
