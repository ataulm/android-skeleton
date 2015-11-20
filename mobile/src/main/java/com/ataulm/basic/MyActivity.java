package com.ataulm.basic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MyActivity extends Activity {

    private BreakfastBar breakfastBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        breakfastBar = BreakfastBar.newInstance(this);

        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarAlpha(0.2f);
        systemBarTintManager.setStatusBarTintEnabled(true);

        int blue = Color.rgb(101, 157, 219);
        int black_twenty = Color.rgb(204, 204, 204);
        int blue_shaded = multiply(blue, black_twenty);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(blue_shaded);
        }
    }

    @ColorInt
    private int multiply(@ColorInt int color1, @ColorInt int color2) {
        int color3a = (Color.alpha(color1) * Color.alpha(color2)) / 255;
        int color3r = (Color.red(color1) * Color.red(color2)) / 255;
        int color3g = (Color.green(color1) * Color.green(color2)) / 255;
        int color3b = (Color.blue(color1) * Color.blue(color2)) / 255;
        return Color.argb(color3a, color3r, color3g, color3b);
    }

    public void showBar(View view) {
        breakfastBar.display("Hello", null, null, null, 1000);
    }

    public void hideBar(View view) {
        breakfastBar.dismiss();
    }

    public void pingThreeBars(View view) {
        breakfastBar.display("ping 1", null, null, null, 1000);
        breakfastBar.display("ping 2", null, null, null, 1000);
        breakfastBar.display("ping 3", null, null, null, 1000);
    }

}
