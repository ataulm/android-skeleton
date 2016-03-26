package com.ataulm.basic.demo;

import android.os.Bundle;
import android.view.View;

public class MyActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void crash(View view) {
        throw new RuntimeException("ruh roh");
    }

}
