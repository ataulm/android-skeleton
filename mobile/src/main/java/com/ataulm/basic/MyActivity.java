package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter());
        tabStrip.setViewPager(pager);
    }

    private enum MyData {
        HELLO,
        WORLD,
        WHAT,
        IS,
        THE,
        HAPS;

        String getFirstLine() {
            return name();
        }

        String getSecondLine() {
            return "2_" + name() + "_2";
        }
    }

    private static class MyAdapter extends PagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

        @Override
        public int getCount() {
            return MyData.values().length;
        }

        @Override
        public final View instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
            TextView view = (TextView) layoutInflater.inflate(R.layout.view_page, container, false);
            view.setText("Page content: " + MyData.values()[position].getFirstLine());
            container.addView(view);
            return view;
        }

        @Override
        public final void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public final boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyData.values()[position].getFirstLine();
        }

        @Override
        public View getCustomTabView(ViewGroup viewGroup, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            MyCustomTabView tabView = (MyCustomTabView) layoutInflater.inflate(R.layout.view_custom_tab, viewGroup, false);
            tabView.setFirstLineText(MyData.values()[position].getFirstLine());
            tabView.setSecondLineTextView(MyData.values()[position].getSecondLine());
            return tabView;
        }

    }

}
