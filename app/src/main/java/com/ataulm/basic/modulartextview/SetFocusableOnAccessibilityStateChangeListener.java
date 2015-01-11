package com.ataulm.basic.modulartextview;

import android.view.View;
import android.view.accessibility.AccessibilityManager;

class SetFocusableOnAccessibilityStateChangeListener implements AccessibilityManager.AccessibilityStateChangeListener {

    private final View view;

    SetFocusableOnAccessibilityStateChangeListener(View view) {
        this.view = view;
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        view.setFocusable(enabled);
    }

}
