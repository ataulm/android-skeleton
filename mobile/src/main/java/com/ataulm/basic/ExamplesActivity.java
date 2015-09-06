package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExamplesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);

        ListView exampleList = (ListView) findViewById(R.id.example_list);
        exampleList.setAdapter(new Examples(getLayoutInflater()));
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
            view.setText(getItem(position).name());
        }

        private enum Example {
            FOO,
            BAR
        }

    }

}
