package com.example;

import android.content.Context;

class AndroidAwareClassUsingPojo {

    private final Context context;
    private final Pojo pojo;

    AndroidAwareClassUsingPojo(Context context, Pojo pojo) {
        this.context = context;
        this.pojo = pojo;
    }

    void updatePojoColor() {
        int color = context.getColor(0);
        pojo.setColor(color);
    }
}
