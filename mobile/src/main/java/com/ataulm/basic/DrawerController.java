package com.ataulm.basic;

import android.support.v4.widget.DrawerLayout;

class DrawerController {

    private final DrawerLayout drawerLayout;
    private final int drawerGravity;

    DrawerController(DrawerLayout drawerLayout, int drawerGravity) {
        this.drawerLayout = drawerLayout;
        this.drawerGravity = drawerGravity;
    }

    boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(drawerGravity);
    }

    void closeDrawer() {
        drawerLayout.closeDrawer(drawerGravity);
    }

    void openDrawer() {
        drawerLayout.openDrawer(drawerGravity);
    }

}
