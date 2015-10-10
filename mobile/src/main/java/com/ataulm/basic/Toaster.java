package com.ataulm.basic;

import android.content.Context;
import android.widget.Toast;

final class Toaster {

    private static final Toaster toaster = new Toaster();

    private Toast toast;

    public static void display(Context context, String message) {
        if (toaster.toast != null) {
            toaster.toast.cancel();
        }
        toaster.toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toaster.toast.show();
    }

}
