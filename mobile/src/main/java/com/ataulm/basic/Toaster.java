package com.ataulm.basic;

import android.content.Context;
import android.widget.Toast;

public final class Toaster {

    private static final Toaster instance = new Toaster();

    private Toast toast;

    private Toaster() {
        // I'm a bad person
    }

    public static void display(Context context, String message) {
        if (instance.toast != null) {
            instance.toast.cancel();
        }
        instance.toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        instance.toast.show();
    }

}
