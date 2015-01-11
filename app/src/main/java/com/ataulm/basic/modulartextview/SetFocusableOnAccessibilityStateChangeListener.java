package com.ataulm.basic.modulartextview;

import android.view.View;
import android.view.accessibility.AccessibilityManager;

class SetFocusableOnAccessibilityStateChangeListener implements AccessibilityManager.AccessibilityStateChangeListener {

    private final View view;

    SetFocusableOnAccessibilityStateChangeListener(View view, boolean accessibilityEnabled) {
        this.view = view;
        onAccessibilityStateChanged(accessibilityEnabled);
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        view.setFocusable(enabled);
    }

}
