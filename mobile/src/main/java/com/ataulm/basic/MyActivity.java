package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        AbsListView listview = (AbsListView) findViewById(R.id.listview);
        listview.setAdapter(new DummyAdapter(getLayoutInflater()));
    }

    private static class DummyAdapter extends BaseAdapter {

        private static final int COUNT = 25;

        private final LayoutInflater layoutInflater;

        DummyAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public String getItem(int position) {
            return "Item " + position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.dummy_item, parent, false);
            }
            ((TextView) view).setText(getItem(position));
            return view;
        }

    }

}
