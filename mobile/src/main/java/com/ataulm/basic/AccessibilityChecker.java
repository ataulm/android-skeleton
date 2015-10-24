package com.ataulm.basic;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

import static android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_SPOKEN;
import static android.content.Context.ACCESSIBILITY_SERVICE;

public class AccessibilityChecker {

    private final AccessibilityManager am;

    public static AccessibilityChecker newInstance(Context c) {
        AccessibilityManager am = (AccessibilityManager) c.getSystemService(ACCESSIBILITY_SERVICE);
        return new AccessibilityChecker(am);
    }

    public AccessibilityChecker(AccessibilityManager am) {
        this.am = am;
    }

    public boolean isSpokenFeedbackEnabled() {
        List spokenFeedbackServices = getEnabledServicesFor(FEEDBACK_SPOKEN);
        return !spokenFeedbackServices.isEmpty();
    }

    private List getEnabledServicesFor(int feedbackTypeFlags) {
        return am.getEnabledAccessibilityServiceList(feedbackTypeFlags);
    }

    public boolean isInNonTouchMode(View view) {
        return !view.isInTouchMode();
    }

}
