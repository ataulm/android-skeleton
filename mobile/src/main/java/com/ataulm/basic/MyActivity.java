package com.ataulm.basic;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.viewpageradapter.ViewPagerAdapter;

public class MyActivity extends AppCompatActivity {

    private static final int COUNT = 5;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter<TextView>() {
            @Override
            public int getCount() {
                return COUNT;
            }

            @Override
            protected TextView createView(ViewGroup container, int position) {
                TextView textView = new TextView(container.getContext());
                textView.setFocusable(true);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }

            @Override
            protected void bindView(TextView view, int position) {
                view.setText("hello " + position);
            }
        });
    }

    public void onClickPrevious(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
        }
    }

    public void onClickNext(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < COUNT - 1) {
            viewPager.setCurrentItem(currentItem + 1);
        }
    }
}
