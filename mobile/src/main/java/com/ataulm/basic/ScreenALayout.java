package com.ataulm.basic;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScreenALayout extends LinearLayout {

    @BindView(R.id.appbar)
    AppBarWidget appBarWidget;

    public ScreenALayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_screen_a, this);
        ButterKnife.bind(this);
    }

}
