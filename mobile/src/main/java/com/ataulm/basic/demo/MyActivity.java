package com.ataulm.basic.demo;

import android.os.Bundle;

public class MyActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        throw new RuntimeException("ruh roh");
    }

}
