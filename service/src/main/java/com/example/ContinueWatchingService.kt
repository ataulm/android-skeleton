package com.example

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.accessibilityservice.AccessibilityServiceInfo.CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT
import android.content.pm.ServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Watches for window changes to scan for a "Continue watching" button to click it for you.
 */
class ContinueWatchingService : AccessibilityService() {

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

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = getServiceInfo()
        info.packageNames = arrayOf("foo", "com.example")
        setServiceInfo(info)
    }
}
