package com.ataulm.basic.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.basic.R;

public class NavigationDrawerHeaderView extends LinearLayout {

    private Listener listener;

    public NavigationDrawerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOrientation(VERTICAL);
        View.inflate(getContext(), R.layout.merge_navigation_drawer_header, this);

        View profile = findViewById(R.id.navigation_drawer_header_view_profile);
        profile.setContentDescription("Logged in as Tina Belcher, click to switch users");
        profile.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickChooseAccounts();
                    }
                }
        );

        ImageView icon = (ImageView) findViewById(R.id.navigation_drawer_header_image_profile);
        icon.setImageResource(R.drawable.tina);
        ((TextView) findViewById(R.id.navigation_drawer_header_text_name)).setText("Tina Belcher");
        ((TextView) findViewById(R.id.navigation_drawer_header_text_email)).setText("tina@zombiefanfictionforums.com");

        View close = findViewById(R.id.navigation_drawer_header_button_close);
        close.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickCloseNavigationDrawerButton();
                    }
                }
        );
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
