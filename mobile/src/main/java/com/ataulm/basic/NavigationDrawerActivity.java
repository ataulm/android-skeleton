package com.ataulm.basic;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class NavigationDrawerActivity extends MockWutsonActivity {

    private static final int DRAWER_GRAVITY = GravityCompat.START;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ViewGroup content;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_top_level);
        content = (ViewGroup) findViewById(R.id.top_level_container_activity_layout);
        content.removeAllViews();
        getLayoutInflater().inflate(layoutResID, content);

        populateNavigationDrawer();
    }

    @Override
    protected int getNavigationIcon() {
        return R.drawable.ic_drawer;
    }

    private void populateNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_drawer_open_content_description, R.string.nav_drawer_close_content_description);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                actionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                actionBarDrawerToggle.onDrawerOpened(drawerView);
                content.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                actionBarDrawerToggle.onDrawerClosed(drawerView);
                content.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                getToolbar().requestFocus();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                actionBarDrawerToggle.onDrawerStateChanged(newState);
            }

        });

        NavigationView navigationView = (NavigationView) drawerLayout.findViewById(R.id.navigation_drawer);
        // TODO: setup nav view
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(DRAWER_GRAVITY);
            return true;
        }
        throw new IllegalArgumentException("Item id not implemented");
    }

    @Override
    public void onBackPressed() {
        if (closeNavigationDrawer()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Closes the navigation drawer if it's open.
     *
     * @return true if navigation drawer was successfully closed, false if it wasn't open anyway
     */
    protected boolean closeNavigationDrawer() {
        if (drawerLayout.isDrawerOpen(DRAWER_GRAVITY)) {
            closeDrawer();
            return true;
        } else {
            return false;
        }
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(DRAWER_GRAVITY);
    }

}
