package com.ataulm.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

class SecretActionsView extends View {

    public SecretActionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setContentDescription("Secret actions inside!");
        ViewCompat.setAccessibilityDelegate(this, new SecretA11yDelegate());
    }

    private static class SecretA11yDelegate extends AccessibilityDelegateCompat {

        @Override
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.action_secret_one, "Do Secret Action One!"));
            info.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.action_secret_two, "Do Secret Action Two!"));
        }

        @Override
        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            switch (action) {
                case R.id.action_secret_one:
                    Toast.makeText(host.getContext(), "such secret (1)!", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_secret_two:
                    Toast.makeText(host.getContext(), "very action (2)!", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.performAccessibilityAction(host, action, args);
            }
        }

    }

}
