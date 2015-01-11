package com.ataulm.basic;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

class AccessibilityDependentViewPropertyUpdater {

    private final AccessibilityManager accessibilityManager;
    private final View[] views;

    public static AccessibilityDependentViewPropertyUpdater newInstance(Context context, View... views) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        return new AccessibilityDependentViewPropertyUpdater(accessibilityManager, views);
    }

    AccessibilityDependentViewPropertyUpdater(AccessibilityManager accessibilityManager, View... views) {
        this.accessibilityManager = accessibilityManager;
        this.views = views;
    }

    public void updateFocusablePropertyForNonInteractiveElements() {
        boolean enableFocus = accessibilityIsEnabled();
        for (View view : views) {
            view.setFocusable(enableFocus);
        }
    }

    private boolean accessibilityIsEnabled() {
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

}
