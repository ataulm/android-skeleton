package com.ataulm.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BreakfastBarLayout extends LinearLayout {

    private TextView actionView;
    private TextView messageView;

    public BreakfastBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(HORIZONTAL);
    }

    @Override
    public final void setOrientation(int orientation) {
        throw new RuntimeException("don't override");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        actionView = (TextView) findViewById(R.id.breakfast_bar_text_action);
        messageView = (TextView) findViewById(R.id.breakfast_bar_text_message);
    }

    public void setMessage(String message) {
        messageView.setText(message);
    }

    public void setAction(String action, OnClickListener clickListener) {
        actionView.setText(action);
        actionView.setOnClickListener(clickListener);
    }

}
