package com.ataulm.basic;

import android.content.Context;
import android.widget.Toast;

class ToastDisplayer {

    private final Context context;
    private Toast toast;

    ToastDisplayer(Context context) {
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
