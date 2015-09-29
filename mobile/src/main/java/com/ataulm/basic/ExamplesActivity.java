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

public class ExamplesActivity extends Activity {

    public static final String EXTRA_VARIANT = "VARIANT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);

        ListView exampleList = (ListView) findViewById(R.id.example_list);
        exampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class<? extends Activity> activityClass = Example.values()[position].getActivityClass();
                Intent intent = new Intent(ExamplesActivity.this, activityClass)
                        .putExtra(EXTRA_VARIANT, Example.values()[position].getVariant());
                startActivity(intent);
            }

        });
        exampleList.setAdapter(new Examples(getLayoutInflater()));
    }

    private enum Example {

        HELLO("Hello", HelloActivity.class),
        DEFAULT_ACTION_LIST("List with no click", ActionListActivity.class, ActionListActivity.VARIANT_NO_CLICK),
        NO_CLICK_FOO_IMAGE_ACTION_LIST("List with no click, but foo image", ActionListActivity.class, ActionListActivity.VARIANT_NO_CLICK_FOO_IMAGE),
        CLICKABLE_ACTION_LIST("List (clickable=\"true\" attr)", ActionListActivity.class, ActionListActivity.VARIANT_CLICKABLE_TRUE),
        CLICK_LISTENER_ACTION_LIST("List (real click listener)", ActionListActivity.class, ActionListActivity.VARIANT_WITH_CLICK),
        FOO_IMAGE_ACTION_LIST("List (imageview with foo content description)", ActionListActivity.class, ActionListActivity.VARIANT_WITH_CLICK_FOO_IMAGE),
        FOO_NOT_IMPORTANT_IMAGE_ACTION_LIST("List (imageview with foo content description, and not important)", ActionListActivity.class, ActionListActivity.VARIANT_WITH_CLICK_FOO_IMAGE_NOT_IMPORTANT_IMAGE),
        CD_ITEM_FOO_IMAGE_ACTION_LIST("List (item with cd, imageview with foo content description)", ActionListActivity.class, ActionListActivity.VARIANT_WITH_CLICK_CD_ON_ITEM_FOO_IMAGE);

        private final String name;
        private final Class<? extends Activity> activityClass;
        private final int variant;

        Example(String name, Class<? extends Activity> activityClass) {
            this(name, activityClass, 0);
        }

        Example(String name, Class<? extends Activity> activityClass, int variant) {
            this.name = name;
            this.activityClass = activityClass;
            this.variant = variant;
        }

        String getName() {
            return name;
        }

        Class<? extends Activity> getActivityClass() {
            return activityClass;
        }

        int getVariant() {
            return variant;
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
            view.setText(item.getName());
        }

    }

}
