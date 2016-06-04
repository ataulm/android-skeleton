package com.ataulm.basic.navigation;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

class MimeTypeResolver {

    private static final String MIME_TYPE_TOP_LEVEL_ACTIVITY = "vnd.android.cursor.item/vnd.%1$s.toplevel";

    private final String packageName;

    static MimeTypeResolver create(Context context) {
        return new MimeTypeResolver(context.getPackageName());
    }

    MimeTypeResolver(String packageName) {
        this.packageName = packageName;
    }

    @Nullable
    public String getType(Uri uri) {
        if (validTopLevel(uri)) {
            return String.format(MIME_TYPE_TOP_LEVEL_ACTIVITY, packageName);
        } else {
            return null;
        }
    }

    private boolean validTopLevel(Uri uri) {
        for (Screen screen : Screen.values()) {
            if (uri.getLastPathSegment().endsWith(screen.getPath())) {
                return true;
            }
        }
        return false;
    }

}
