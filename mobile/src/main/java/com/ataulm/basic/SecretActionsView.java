package com.ataulm.basic;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

class SecretActionsView extends View {

    public SecretActionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
        event.getText().add("Foo");
        event.getText().add("Bar");

        log("onPopulateAccessibilityEvent");
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        addAccessibilityAction(info, R.id.action_toast_secret_one, "Perform secret toast 1");
    }

    @SuppressWarnings("deprecation") // AccessibilityNodeInfo.addAction(int) is deprecated
    private void addAccessibilityAction(AccessibilityNodeInfo info, @IdRes int action, CharSequence label) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            info.addAction(new AccessibilityNodeInfo.AccessibilityAction(action, label));
        } else {
            info.addAction(action);
        }
    }

    @Override
    public boolean performAccessibilityAction(int action, Bundle args) {
        if (action == R.id.action_toast_secret_one) {
            Toast.makeText(getContext(), "secret one!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.performAccessibilityAction(action, args);
        }
    }

    private void log(String message) {
        Log.d("SecretActionsView", message);
    }

}
