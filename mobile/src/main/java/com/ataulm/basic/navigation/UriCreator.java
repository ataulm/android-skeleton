package com.ataulm.basic.navigation;

import android.content.Context;
import android.net.Uri;

public class UriCreator {

    private final String authority;

    public static UriCreator create(Context context) {
        return new UriCreator(context.getPackageName());
    }

    UriCreator(String authority) {
        this.authority = authority;
    }

    public Uri createUriToView(Screen screen) {
        String uri = String.format("content://%1$s/%2$s", authority, screen.getPath());
        return Uri.parse(uri);
    }

}
