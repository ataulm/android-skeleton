package com.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityViewCommand
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        ViewCompat.setAccessibilityHeading(primaryButton, true)
        ViewCompat.addAccessibilityAction(primaryButton, "custom action") { view: View, commandArguments: AccessibilityViewCommand.CommandArguments? ->
            // TODO do something
            true
        }
        ViewCompat.setAccessibilityDelegate(primaryButton, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.addAction( AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        R.id.fooo, "custom action2"
                ) )
            }
        })

        // this one overrides the previous accessibility delegate such that only `custom action` and `custom action3` are available (isHeading is also preserved)
        ViewCompat.setAccessibilityDelegate(primaryButton, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.addAction( AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        R.id.bar, "custom action3"
                ) )
            }
        })
    }
}
