package com.example;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.landingstrip.BaseAdapter;
import com.novoda.landingstrip.LandingStrip;
import com.novoda.viewpageradapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MyActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LandingStrip outerTabStrip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        outerTabStrip = findViewById(R.id.tab_strip);
        viewPager = findViewById(R.id.view_pager);
        final Sentence sentence = new Sentence(new Words("I", "should", "be"), new Words("working", "on", "my"), new Words("slides", "right", "now"));
        viewPager.setAdapter(new OuterPageAdapter(sentence, outerTabStrip));

        viewPager.addOnPageChangeListener(outerTabStrip);
        outerTabStrip.setAdapter(new BaseAdapter<LandingStrip>() {
            @Override
            protected LandingStrip createView(ViewGroup parent, int position) {
                return (LandingStrip) getLayoutInflater().inflate(R.layout.view_outer_tab, parent, false);
            }

            @Override
            protected void bindView(LandingStrip tabStrip, final int outerPosition) {
                tabStrip.setAdapter(new BaseAdapter<TextView>() {
                    @Override
                    protected TextView createView(ViewGroup parent, int position) {
                        return (TextView) getLayoutInflater().inflate(R.layout.view_tab, parent, false);
                    }

                    @Override
                    protected void bindView(TextView view, int innerPosition) {
                        String word = sentence.get(outerPosition).get(innerPosition);
                        view.setText(word);
                    }

                    @Override
                    protected int getCount() {
                        return sentence.get(outerPosition).size();
                    }
                });
            }

            @Override
            protected int getCount() {
                return sentence.size();
            }
        });
    }

    private static class Sentence extends ArrayList<Words> {

        Sentence(Words... sentence) {
            super(Arrays.asList(sentence));
        }
    }

    private static class Words extends ArrayList<String> {

        Words(String... words) {
            super(Arrays.asList(words));
        }
    }

    private static class OuterPageAdapter extends ViewPagerAdapter<OuterPageView> {

        private final Sentence sentence;
        private final ViewGroup outerTabStrip;

        OuterPageAdapter(Sentence sentence, ViewGroup outerTabStrip) {
            this.sentence = sentence;
            this.outerTabStrip = outerTabStrip;
        }

        @Override
        protected OuterPageView createView(ViewGroup container, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
            return (OuterPageView) layoutInflater.inflate(R.layout.view_outer_page, container, false);
        }

        @Override
        protected void bindView(OuterPageView view, int position) {
            view.addOnPageChangeListener((ViewPager.OnPageChangeListener) ((ViewGroup) outerTabStrip.getChildAt(0)).getChildAt(position));
            view.setAdapter(new InnerPageAdapter(sentence.get(position)));
        }

        @Override
        public int getCount() {
            return sentence.size();
        }
    }

    private static class InnerPageAdapter extends ViewPagerAdapter<InnerPageView> {

        private final Words words;

        InnerPageAdapter(Words words) {
            this.words = words;
        }

        @Override
        protected InnerPageView createView(ViewGroup container, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
            return (InnerPageView) layoutInflater.inflate(R.layout.view_inner_page, container, false);
        }

        @Override
        protected void bindView(InnerPageView view, int position) {
            view.setText(words.get(position));
        }

        @Override
        public int getCount() {
            return words.size();
        }
    }
}
