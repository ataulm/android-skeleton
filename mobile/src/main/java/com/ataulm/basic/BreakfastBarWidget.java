package com.ataulm.basic;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BreakfastBarWidget extends LinearLayout {

    private TextView messageView;
    private TextView actionView;
    private View dismissView;

    public BreakfastBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(HORIZONTAL);
    }

    @Override
    public final void setOrientation(int orientation) {
        throw new RuntimeException("aklfjasdl;fkj");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_breakfast_bar, this);
        messageView = (TextView) findViewById(R.id.breakfast_bar_text_message);
        actionView = (TextView) findViewById(R.id.breakfast_bar_text_action);
        dismissView = findViewById(R.id.breakfast_bar_text_dismiss);
    }

    public void showDismissAction(boolean shouldShow) {
        dismissView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    public void set(final Callback callback) {
        dismissView.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onRequestDismiss();
                    }
                }
        );
    }

    public void setMessage(String message) {
        messageView.setText(message);
    }

    public void setAction(String action, OnClickListener clickListener) {
        if (action == null || TextUtils.isEmpty(action) || clickListener == null) {
            actionView.setVisibility(GONE);
        } else {
            actionView.setVisibility(VISIBLE);
        }
        actionView.setText(action);
        actionView.setOnClickListener(clickListener);
    }

    interface Callback {

        void onRequestDismiss();

    }

}
