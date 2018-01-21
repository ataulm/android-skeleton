package com.example

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent


class LogKeyEventService : AccessibilityService() {

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        when (event?.eventType) {
            AccessibilityEvent.TYPE_GESTURE_DETECTION_START -> {
                if (event?.source != null) {
                    Log.d("!!!", "foo")
                }
            }

            else -> {
                if (event?.source != null) {
                    Log.d("!!!", "bar")
                }
            }
        }
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        print(event!!)
        return false
    }

    private fun print(event: KeyEvent) {
        Log.d("!!!", "action " + event.action)
        Log.d("!!!", "code " + event.keyCode)
    }
}
