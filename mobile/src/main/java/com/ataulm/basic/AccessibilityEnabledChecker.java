package com.ataulm.basic;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.view.accessibility.AccessibilityManager;

final class AccessibilityEnabledChecker {

    private final AccessibilityManager accessibilityManager;

    public static AccessibilityEnabledChecker newInstance(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        return new AccessibilityEnabledChecker(accessibilityManager);
    }

    private AccessibilityEnabledChecker(AccessibilityManager accessibilityManager) {
        this.accessibilityManager = accessibilityManager;
    }

    public boolean isTouchExplorationEnabled() {
        return AccessibilityManagerCompat.isTouchExplorationEnabled(accessibilityManager);
    }

}
