package com.example.ext

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

fun View.removeAccessibilityActions() {
    for (action in getActionList()) {
        ViewCompat.removeAccessibilityAction(this, action.id)
    }
}

private fun View.getActionList(): List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> {
    return AccessibilityNodeInfoCompat.obtain(this)
            .apply { ViewCompat.onInitializeAccessibilityNodeInfo(this@getActionList, this) }
            .actionList
}
