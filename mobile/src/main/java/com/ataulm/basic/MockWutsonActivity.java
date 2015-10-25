package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public abstract class MockWutsonActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (hasNoAppBar()) {
            return;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        setAppBar(toolbar);
    }

    private void setAppBar(Toolbar toolbar) {
        if (hasNoAppBar()) {
            return;
        }
        toolbar.setNavigationIcon(getNavigationIcon());
        super.setSupportActionBar(toolbar);
    }

    protected int getNavigationIcon() {
        return R.drawable.ic_up;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean hasNoAppBar() {
        return false;
    }

}
