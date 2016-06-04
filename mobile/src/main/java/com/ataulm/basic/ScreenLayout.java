package com.ataulm.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScreenLayout extends LinearLayout {

    @BindView(R.id.appbar)
    AppBarWidget appBarWidget;

    @BindView(R.id.content)
    TextView contentTextView;

    private String title;
    private String content;

    public ScreenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(VERTICAL);
        applyCustomAttrs(context, attrs);
    }

    private void applyCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScreenLayout);
        title = typedArray.getString(R.styleable.ScreenLayout_screenTitle);
        content = typedArray.getString(R.styleable.ScreenLayout_screenContent);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_screen, this);
        ButterKnife.bind(this);

        appBarWidget.setTitle(title);
        appBarWidget.showNavigationDrawerIcon(new AppBarWidget.NavDrawerIconClickListener() {
            @Override
            public void onClick() {
                // TODO: open drawer properly with a callback to the activity
                ((DrawerLayout) ((TopLevelActivity) getContext()).findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.START);
            }
        });
        contentTextView.setText(content);
    }

}
