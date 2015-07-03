package com.ataulm.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BreakfastBar extends LinearLayout {

    private long autoDismissDelayMillis;

    public BreakfastBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreakfastBar setAutoDismiss(long delayMillis) {
        this.autoDismissDelayMillis = delayMillis;
        return this;
    }

    public void display(String message, String action, InteractionListener interactionListener) {
        setVisibility(VISIBLE);
        if (shouldAutoDismiss()) {
            startDismissTimer();
        }
    }

    private void startDismissTimer() {
        // TODO: start timer
    }

    private boolean shouldAutoDismiss() {
        return autoDismissDelayMillis > 0;
    }

    public void dismiss() {
        setVisibility(GONE);
        if (shouldAutoDismiss()) {
            cancelDismissTimer();
        }
    }

    private void cancelDismissTimer() {
        // TODO: stop timer
    }

    public interface InteractionListener {

        void onClickAction();

        void onClickDismiss();

        void onAutoDismiss();

    }

}