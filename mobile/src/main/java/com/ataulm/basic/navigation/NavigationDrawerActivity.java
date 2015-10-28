package com.ataulm.basic.navigation;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ataulm.basic.R;
import com.ataulm.basic.Toaster;

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
        return R.drawable.ic_action_drawer;
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

        NavigationDrawerView navigationView = (NavigationDrawerView) drawerLayout.findViewById(R.id.navigation_drawer);
        syncCurrentPageWith(navigationView);
        navigationView.setListener(new Listener() {
            @Override
            public void onClickCloseNavigationDrawerButton() {
                closeDrawer();
            }

            @Override
            public void onClickChooseAccounts() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                DialogFragment newFragment = new AddAccountDialog();
                newFragment.show(ft, "dialog");
            }

            @Override
            public void onClickItem(String item) {
                Toaster.display(NavigationDrawerActivity.this, "clicked " + item);
            }
        });

    }

    private static class AddAccountDialog extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().setTitle("Choose an account");

            View view = inflater.inflate(R.layout.view_choose_account_dialog, container, false);
            ((ChooseAccountItemView) view.findViewById(R.id.choose_account_dialog_item_louise)).update(R.drawable.louise, "Louise Belcher");
            ((ChooseAccountItemView) view.findViewById(R.id.choose_account_dialog_item_gene)).update(R.drawable.gene, "Gene Belcher");
            ((ChooseAccountItemView) view.findViewById(R.id.choose_account_dialog_item_add_new)).update(R.drawable.account_add, "Add new account");
            return view;
        }

    }

    protected abstract void syncCurrentPageWith(NavigationDrawerView navigationDrawerView);

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
        return super.onOptionsItemSelected(item);
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
