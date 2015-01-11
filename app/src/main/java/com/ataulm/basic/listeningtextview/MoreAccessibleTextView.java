package com.ataulm.basic.listeningtextview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

public class MoreAccessibleTextView extends TextView {

    private final AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener;

    public MoreAccessibleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoreAccessibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        accessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() {

            @Override
            public void onAccessibilityStateChanged(boolean enabled) {
                setFocusable(enabled);
            }

        };
    }

    @Override
    protected void onFinishInflate() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(accessibilityStateChangeListener);
        // need the initial kick up the butt
        setFocusable(accessibilityManager.isEnabled());
    }

}
