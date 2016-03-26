package com.ataulm.basic.demo;

import android.app.Application;

import com.ataulm.basic.OpenUhOhUncaughtExceptionHandler;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.UncaughtExceptionHandler handler = OpenUhOhUncaughtExceptionHandler.newInstance(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

}
