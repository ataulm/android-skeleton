package com.ataulm.basic.navigation;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ataulm.basic.R;

public class NavigationDrawerActionItemView extends LinearLayout {

    private TextView titleTextView;
    private Switch switchView;

    public NavigationDrawerActionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOrientation(HORIZONTAL);
        View.inflate(getContext(), R.layout.merge_navigation_drawer_action_item, this);

        titleTextView = (TextView) findViewById(R.id.navigation_drawer_item_text_title);
        switchView = (Switch) findViewById(R.id.navigation_drawer_item_switch);
    }

    public void update(String title) {
        titleTextView.setText(title);
    }

}
