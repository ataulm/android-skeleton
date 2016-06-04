package com.ataulm.basic.navigation;

import android.app.Activity;
import android.content.Intent;

public class Navigator {

    private final Activity activity;
    private final UriCreator uriCreator;

    public static Navigator create(Activity activity) {
        return new Navigator(activity, UriCreator.create(activity));
    }

    Navigator(Activity activity, UriCreator uriCreator) {
        this.activity = activity;
        this.uriCreator = uriCreator;
    }

    public void navigateTo(Screen screen) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uriCreator.createUriToView(screen));
        activity.startActivity(intent);
        activity.finish();
    }

}
