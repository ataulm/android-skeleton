package com.ataulm.basic.modulartextview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import static android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;

public class ModularMoreAccessibleTextView extends TextView {

    public ModularMoreAccessibleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ModularMoreAccessibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

        boolean initialAccessibilityEnabledValue = accessibilityManager.isEnabled();
        AccessibilityStateChangeListener listener = new SetFocusableOnAccessibilityStateChangeListener(this, initialAccessibilityEnabledValue);

        accessibilityManager.addAccessibilityStateChangeListener(listener);
    }

}
