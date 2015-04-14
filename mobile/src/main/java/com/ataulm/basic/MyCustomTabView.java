package com.ataulm.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyCustomTabView extends LinearLayout {

    private TextView firstLineTextView;
    private TextView secondLineTextView;

    public MyCustomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstLineTextView = (TextView) findViewById(R.id.first_line_text);
        secondLineTextView = (TextView) findViewById(R.id.second_line_text);
    }

    void setFirstLineText(String firstLineText) {
        firstLineTextView.setText(firstLineText);
    }

    void setSecondLineTextView(String secondLineText) {
        secondLineTextView.setText(secondLineText);
    }

}
