package com.ataulm.basic;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public final class BreakfastBar {

    private final BreakfastBarWidget widget;

    private long autoDismissDelayMillis;
    private Callback dismissAction;

    public static BreakfastBar newInstance(Activity activity) {
        BreakfastBarWidget breakfastBar = (BreakfastBarWidget) activity.getWindow().getDecorView().findViewById(R.id.breakfast_bar);
        if (breakfastBar == null) {
            breakfastBar = inflateBreakfastBarWidget(activity);
        }
        return new BreakfastBar(breakfastBar);
    }

    @Nullable
    private static BreakfastBarWidget inflateBreakfastBarWidget(Activity activity) {
        View contentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup parent = findASuitableViewGroup(contentView);
        if (parent == null) {
            return null;
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        inflater.inflate(R.layout.view_breakfastbar, parent, true);
        return (BreakfastBarWidget) parent.findViewById(R.id.breakfast_bar);
    }

    private BreakfastBar(BreakfastBarWidget widget) {
        if (widget == null) {
            throw new IllegalArgumentException("Null widget");
        }
        this.widget = widget;
    }

    public BreakfastBar withDismissAction(boolean showDismissAction) {
        widget.showDismissAction(showDismissAction);
        return this;
    }

    private void disableSwipeToDismiss() {
        widget.setOnTouchListener(null);
    }

    public void display(String message) {
        display(message, null, null, null, 0);
    }

    public void display(String message, Callback dismissAction) {
        display(message, null, null, dismissAction, 0);
    }

    public void display(@StringRes int message) {
        display(widget.getResources().getString(message), null, null, null, 0);
    }

    public void display(
            String message,
            @Nullable String action,
            @Nullable View.OnClickListener onClickListener,
            @Nullable Callback dismissAction,
            long autoDismissDelayMillis
    ) {
        dismiss();
        this.dismissAction = dismissAction;
        this.autoDismissDelayMillis = autoDismissDelayMillis;

        widget.setMessage(message);
        widget.setAction(action, onClickListener);
        widget.setVisibility(View.VISIBLE);
        widget.set(
                new BreakfastBarWidget.Callback() {
                    @Override
                    public void onRequestDismiss() {
                        dismiss();
                    }
                }
        );

        if (shouldAutoDismissAfterDelay()) {
            cancelDismissTimer();
            startDismissTimer();
        }
    }

    public void dismiss() {
        if (widget.getVisibility() == View.GONE) {
            return;
        }

        widget.setVisibility(View.GONE);
        cancelDismissTimer();
        if (dismissAction != null) {
            dismissAction.onDismiss();
            dismissAction = null;
        }
    }

    private void cancelDismissTimer() {
        Handler handler = widget.getHandler();
        if (handler != null) {
            handler.removeCallbacks(hideWidgetRunnable);
        }
    }

    private final Runnable hideWidgetRunnable = new Runnable() {

        @Override
        public void run() {
            dismiss();
        }

    };

    private void startDismissTimer() {
        widget.getHandler().postDelayed(hideWidgetRunnable, autoDismissDelayMillis);
    }

    private boolean shouldAutoDismissAfterDelay() {
        return autoDismissDelayMillis > 0;
    }

    public interface Callback {

        void onDismiss();

    }

    @Nullable
    private static ViewGroup findASuitableViewGroup(View view) {
        if (view instanceof CoordinatorLayout) {
            return (ViewGroup) view;
        }
        if (view instanceof FrameLayout) {
            return (ViewGroup) view;
        }

        return null;
    }

}