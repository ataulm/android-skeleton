package com.ataulm.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ItemView extends TextView {

    private int position;
    private String place;

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        update(position);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        update(position);
    }

    public void update(String place) {
        this.place = place;
    }

    public void update(int position) {
        this.position = position;
        String state = isPressed() ? "pressed" : isFocused() ? "focused" : "default";
        setText("Item " + position +
                "\nw: " + getMeasuredWidth() +
                "\nh: " + getMeasuredHeight() +
                "\n" + place +
                "\n" + state);
    }

}
