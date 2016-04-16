package com.ataulm.basic;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class ViewIdLoggingAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            Log.d("!# onAccessibilityEvent", "source was null for: " + event);
        } else {
            String viewIdResourceName = source.getViewIdResourceName();
            Log.d("!# onAccessibilityEvent", "viewid: " + viewIdResourceName);
        }
    }

    @Override
    public void onInterrupt() {
        Log.d("!# onInterrupt", "called");
    }

}
