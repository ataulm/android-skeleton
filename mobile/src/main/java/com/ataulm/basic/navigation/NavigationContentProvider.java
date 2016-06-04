package com.ataulm.basic.navigation;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.ataulm.basic.SimpleContentProvider;

public class NavigationContentProvider extends SimpleContentProvider {

    private MimeTypeResolver mimeTypeResolver;

    @Override
    public boolean onCreate() {
        this.mimeTypeResolver = MimeTypeResolver.create(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return mimeTypeResolver.getType(uri);
    }

}
