package com.ataulm.basic;

import android.net.Uri;
import android.support.annotation.Nullable;

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
