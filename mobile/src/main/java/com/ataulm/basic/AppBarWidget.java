package com.ataulm.basic;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppBarWidget extends AppBarLayout {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public AppBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_appbar_default, this);
        ButterKnife.bind(this);

        if (toolbar == null) {
            throw new IllegalStateException("This widget must contain a Toolbar with R.id.toolbar");
        }
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void showNavigationDrawerIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void showUpIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_action_up);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
