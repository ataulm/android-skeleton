package com.ataulm.basic.navigation;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.basic.R;

public class NavigationDrawerPrimaryItemView extends LinearLayout {

    private ImageView iconImageView;
    private TextView titleTextView;

    public NavigationDrawerPrimaryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOrientation(HORIZONTAL);
        View.inflate(getContext(), R.layout.merge_navigation_drawer_primary_item, this);

        iconImageView = (ImageView) findViewById(R.id.navigation_drawer_item_image_icon);
        titleTextView = (TextView) findViewById(R.id.navigation_drawer_item_text_title);
    }

    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (activated) {
            iconImageView.setColorFilter(getColor(R.color.navigation_drawer_item_text_activated));
        } else {
            iconImageView.setColorFilter(getColor(R.color.navigation_drawer_item_text_default));
        }
    }

    @ColorInt
    private int getColor(@ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(colorRes, null);
        } else {
            return getResources().getColor(colorRes);
        }
    }

    public void update(@DrawableRes int icon, String title) {
        iconImageView.setImageResource(icon);
        titleTextView.setText(title);
    }

}
