package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.beta.Beta;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;

public class MyActivity extends AppCompatActivity {

    private EventTracker eventTracker;
    private EventFactory eventFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new Beta());
        setContentView(R.layout.activity_my);

        eventTracker = EventTracker.newInstance();
        eventFactory = new EventFactory();
    }

    public void onClickEventOne(View view) {
        Event event = eventFactory.createUserEnabledTalkBack();
        eventTracker.track(event);
    }

    public void onClickEventTwo(View view) {
        Event event = eventFactory.createUserDisabledTalkBack();
        eventTracker.track(event);
    }

    public void onClickEventThree(View view) {
        Event event = eventFactory.createAppLaunchLoadTime(getRandomDuration(), TimeUnit.SECONDS);
        eventTracker.track(event);
    }

    private long getRandomDuration() {
        return (long) (new Random().nextFloat() * 1000);
    }

}
