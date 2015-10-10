package com.ataulm.basic;

import android.content.Context;
import android.content.Intent;

enum Example {

    ALPHA("Alpha", "Simple activity with label and button, nothing extra done", AlphaActivity.class);

    private final String title;
    private final String description;
    private final Class activityClass;

    Example(String title, String description, Class activityClass) {
        this.title = title;
        this.description = description;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Intent getIntent(Context context) {
        return new Intent(context, activityClass);
    }

}
