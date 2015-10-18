package com.ataulm.basic;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class AccessibilityChecker {

    private final AccessibilityManager accessibilityManager;

    public static AccessibilityChecker newInstance(Context context) {
        return new AccessibilityChecker((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE));
    }

    public AccessibilityChecker(AccessibilityManager accessibilityManager) {
        this.accessibilityManager = accessibilityManager;
    }

    public boolean isSpokenFeedbackEnabled() {
        List<AccessibilityServiceInfo> enabledServices = getEnabledServicesFor(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
        return !enabledServices.isEmpty();
    }

    public boolean isInNonTouchMode(View view) {
        return !view.isInTouchMode();
    }

    private List<AccessibilityServiceInfo> getEnabledServicesFor(int feedbackTypeFlags) {
        return accessibilityManager.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

}
