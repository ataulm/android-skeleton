package com.example;

import android.content.Context;

public class AndroidAwareClassUsingPojo {

    private final Context context;
    private final Pojo pojo;

    public AndroidAwareClassUsingPojo(Context context, Pojo pojo) {
        this.context = context;
        this.pojo = pojo;
    }

    public void updatePojoColor() {
        int color = context.getColor(0);
        pojo.setColor(color);
    }

}
