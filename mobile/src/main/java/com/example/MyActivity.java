package com.example;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Lifecycle lifecycle = ProcessLifecycleOwner.get().getLifecycle();
        lifecycle.addObserver(new LoggingLifecycleObserver());
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivityInApp.class);
        startActivity(intent);
    }

    private static class LoggingLifecycleObserver implements LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onApplicationMovedToForeground() {
            Log.d("!!! ", "onApplicationMovingToForeground");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onApplicationMovedToBackground() {
            Log.d("!!! ", "onApplicationMovingToBackground");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onApplicationDestroyed() {
            Log.d("!!! ", "onApplicationDestroyed");
        }
    }
}
