package com.ataulm.basic;

import android.content.Context;

public class AppNameProvider {

    private final Context context;

    AppNameProvider(Context context) {
        this.context = context;
    }

    public String getAppName() {
        return context.getString(R.string.app_name);
    }

}
