package com.ataulm.basic;

import android.content.Context;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LetterView extends LinearLayout {

    private ImageView favouriteView;
    private TextView label;

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOrientation(VERTICAL);
        View.inflate(getContext(), R.layout.merge_letter, this);

        label = (TextView) findViewById(R.id.label);
        favouriteView = (ImageView) findViewById(R.id.favourite);

        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
            @Override
            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onInitializeAccessibilityEvent(host, event);
                Log.e("FOOBAR", "1 " + label.getText());
            }

            @Override
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
//                Log.e("FOOBAR", "2 " + label.getText());
//                int movementGranularities = info.getMovementGranularities();
//                Log.e("FOOBAR", "movementGran: " + movementGranularities);
//                if ((movementGranularities & AccessibilityNodeInfoCompat.MOVEMENT_GRANULARITY_PAGE) == 0) {
//                    Log.e("FOOBAR", "not important");
//                    favouriteView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
//                } else {
//                    Log.e("FOOBAR", "yes important");
//                    favouriteView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
//                }
            }



            @Override
            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                Log.e("FOOBAR", "3 " + label.getText());
                return super.dispatchPopulateAccessibilityEvent(host, event);
            }

            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                Log.e("FOOBAR", "4 " + label.getText());
            }

//            @Override
//            public boolean performAccessibilityAction(View host, int action, Bundle arguments) {
//                Log.e("FOOBAR", "perform accessibility action " + action);
//                if (action == AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY) {
//                    int granularity = arguments.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT);
//                    if (granularity == PARAGRAPH_GRANULARITY) {
//                        favouriteView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
//                    }
//                    Log.e("FOOBAR", "next at movement : " + granularity);
//                    return true;
//                }
//                return super.performAccessibilityAction(host, action, arguments);
//            }

            @Override
            public void sendAccessibilityEvent(View host, int eventType) {
                super.sendAccessibilityEvent(host, eventType);
                Log.e("FOOBAR", "sendA11yEvent " + eventType);
            }

        });

    }

    // TODO: these should use bitmasks
    private static final int PARAGRAPH_GRANULARITY = 8;
    private static final int WORD_GRANULARITY = 2;
    private static final int CHARACTER_GRANULARITY = 1;

}
