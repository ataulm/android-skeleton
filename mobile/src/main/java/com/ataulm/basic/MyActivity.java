package com.ataulm.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.vpa.ViewPagerAdapter;

public class MyActivity extends Activity {

    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter whatAdapter = new WhatAdapter(this);
        viewPager.setAdapter(whatAdapter);
    }

    private static class WhatAdapter extends ViewPagerAdapter {

        private final Context context;

        WhatAdapter(Context context) {
            this.context = context;
        }

        enum What {
            FIRST,
            SECOND,
            THIRD,
            FOURTH,
            FIFTH,
            SIXTH,
            SEVENTH,
            EIGHTH,
            NINTH,
            TENTH;
        }

        @Override
        protected View getView(ViewGroup container, int position) {
            TextView view = new TextView(context);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setGravity(Gravity.CENTER);
            view.setText(What.values()[position].name());
            return view;
        }

        @Override
        public int getCount() {
            return What.values().length;
        }

    }

}
