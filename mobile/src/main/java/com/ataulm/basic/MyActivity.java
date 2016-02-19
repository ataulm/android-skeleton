package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyActivity extends AppCompatActivity {

    private final LondonUndergroundRemoteApi api = new LondonUndergroundRemoteApi();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

}
