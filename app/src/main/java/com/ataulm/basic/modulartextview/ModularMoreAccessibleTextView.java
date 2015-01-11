package com.ataulm.basic.modulartextview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

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
        AccessibilityManager.AccessibilityStateChangeListener listener = new SetFocusableOnAccessibilityStateChangeListener(this);
        accessibilityManager.addAccessibilityStateChangeListener(listener);

        // need the initial kick up the butt
        setFocusable(accessibilityManager.isEnabled());
    }

}
