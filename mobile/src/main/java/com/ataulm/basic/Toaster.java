package com.ataulm.basic;

import android.content.Context;
import android.widget.Toast;

class Toaster {

    private final Context context;
    private Toast toast;

    Toaster(Context context) {
        this.context = context;
    }

    public void display(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
