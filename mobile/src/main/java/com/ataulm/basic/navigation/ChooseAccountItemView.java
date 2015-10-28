package com.ataulm.basic.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.basic.R;

public class ChooseAccountItemView extends LinearLayout {

    public ChooseAccountItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOrientation(HORIZONTAL);
        View.inflate(getContext(), R.layout.merge_choose_account_item, this);
    }

    public void update(int profile, String name) {
        ((ImageView) findViewById(R.id.choose_account_item_image_profile)).setImageResource(profile);
        ((TextView) findViewById(R.id.choose_account_item_text_name)).setText(name);
    }

}
