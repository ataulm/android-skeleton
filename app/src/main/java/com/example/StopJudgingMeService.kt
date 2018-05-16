package com.example

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Watches for window changes to scan for a "Continue watching" button to click it for you.
 */
class StopJudgingMeService : AccessibilityService() {

    override fun onInterrupt() {
        // no op - this has no feedback so there'll be nothing to interrupt
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val list = event?.source?.findAccessibilityNodeInfosByText("continue watching").orEmpty()
        if (list.isEmpty()) {
            return
        }
        list.first()?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }
}
