package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.beta.Beta;

import io.fabric.sdk.android.Fabric;

public class MyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new Beta());
        setContentView(R.layout.activity_my);
    }

    public void onClickEventOne(View view) {
    }

    public void onClickEventTwo(View view) {
    }

    public void onClickEventThree(View view) {
    }

}
