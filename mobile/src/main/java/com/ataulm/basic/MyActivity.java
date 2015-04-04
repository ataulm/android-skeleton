package com.ataulm.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ataulm.vpa.ViewPagerAdapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

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
            TextView view = new ClickTextView(context);
            view.setId(position); // without a view ID, child state is not stored
            view.setText(What.values()[position].name());
            return view;
        }

        @Override
        public int getCount() {
            return What.values().length;
        }

    }

    static class ClickTextView extends TextView {

        public static final String KEY_CLICKS = "CLICK_COUNT";
        public static final String KEY_SUPER_STATE = "SUPER_STATE";

        private int clickCount;
        private CharSequence text;

        public ClickTextView(Context context) {
            super(context);
            setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            setGravity(Gravity.CENTER);
            setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    clickCount++;
                    updateText();
                }

            });
        }

        @Override
        public Parcelable onSaveInstanceState() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
            bundle.putInt(KEY_CLICKS, clickCount);
            return bundle;
        }

        @Override
        public void onRestoreInstanceState(Parcelable state) {
            if (!(state instanceof Bundle) || !((Bundle) state).containsKey(KEY_SUPER_STATE)) {
                super.onRestoreInstanceState(state);
                return;
            }
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
            clickCount = bundle.getInt(KEY_CLICKS);
            updateText();
        }

        private void updateText() {
            if (text == null) {
                text = getText();
            }
            setText(text + ": " + String.valueOf(clickCount));
        }

    }

}
