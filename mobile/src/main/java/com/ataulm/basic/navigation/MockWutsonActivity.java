package com.ataulm.basic.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ataulm.basic.R;

public abstract class MockWutsonActivity extends AppCompatActivity {

    private Toolbar toolbar;

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
        toolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        setAppBar(toolbar);
    }

    private void setAppBar(Toolbar toolbar) {
        if (hasNoAppBar()) {
            return;
        }
        // todo: set the correct content description
        toolbar.setNavigationIcon(getNavigationIcon());
        super.setSupportActionBar(toolbar);
    }

    protected int getNavigationIcon() {
        return R.drawable.ic_action_back;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        throw new IllegalArgumentException("Item id not implemented");
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected boolean hasNoAppBar() {
        return false;
    }

}
