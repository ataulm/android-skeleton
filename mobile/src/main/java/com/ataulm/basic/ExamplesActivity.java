package com.ataulm.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class ExamplesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);

        /*
            ListView supports dpad navigation by default (focus states, API-specific styles) but you need to use
            setOnItemClickListener because using setOnClickListener on item views is only partially supported: touch
            clicks will work, but not dpad clicks
         */
        ListView exampleList = (ListView) findViewById(R.id.example_list);
        exampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class<? extends Activity> activityClass = Example.values()[position].getActivityClass();
                Intent intent = new Intent(ExamplesActivity.this, activityClass);
                startActivity(intent);
            }

        });
        exampleList.setAdapter(new Examples(getLayoutInflater()));
    }

    private enum Example {

        TRIVIAL(TrivialActivity.class),
        LOTS_OF_TEXT_IN_SINGLE_VIEW(LongTextActivity.class),
        NON_FOCUSABLE_RV(NonFocusableItemRecyclerViewActivity.class),
        LINEAR_LAYOUT_MANAGER(LinearRecyclerViewActivity.class),
        GRID_LAYOUT_MANAGER(GridRecyclerViewActivity.class),
        BIG_GRID_LAYOUT_MANAGER(BigGridRecyclerViewActivity.class),
        GRID_WITH_ACTIONS_LAYOUT_MANAGER(GridWithActionsRecyclerViewActivity.class);

        private final Class<? extends Activity> activityClass;

        Example(Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
        }

        Class<? extends Activity> getActivityClass() {
            return activityClass;
        }

    }

    private static class Examples extends BaseAdapter {

        private final LayoutInflater layoutInflater;

        Examples(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return Example.values().length;
        }

        @Override
        public Example getItem(int position) {
            return Example.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).ordinal();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.view_examples_item, parent, false);
            }
            bindView(position, (TextView) view);
            return view;
        }

        private void bindView(int position, TextView view) {
            Example item = getItem(position);
            view.setText(item.name().replace('_', ' ').toLowerCase(Locale.UK));
        }

    }

}
