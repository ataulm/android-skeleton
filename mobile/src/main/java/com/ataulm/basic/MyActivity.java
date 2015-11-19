package com.ataulm.basic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarAlpha(0.2f);
        systemBarTintManager.setStatusBarTintEnabled(true);

        int blue = Color.rgb(101, 157, 219);
        int black_twenty = Color.rgb(204, 204, 204);
        int blue_shaded = multiply(blue, black_twenty);

        findViewById(R.id.color).setBackgroundColor(blue);
        findViewById(R.id.shaded).setBackgroundColor(blue_shaded);
        findViewById(R.id.calculated_blue_with_black_twenty).setBackgroundColor(black_twenty);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(blue_shaded);
        }

//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            // Get Window
//            final Window window  = getWindow();
//            // Set Fullscreen
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            );
//            // Set status bar color
//            // ! Can also be set in style resource (/res/values-v21/styles.xml)
//            // <item name="android:statusBarColor">@android:color/transparent</item>
//        }

    }

    @ColorInt
    private int multiply(@ColorInt int color1, @ColorInt int color2) {
        int color3a = (Color.alpha(color1) * Color.alpha(color2)) / 255;
        int color3r = (Color.red(color1) * Color.red(color2)) / 255;
        int color3g = (Color.green(color1) * Color.green(color2)) / 255;
        int color3b = (Color.blue(color1) * Color.blue(color2)) / 255;
        return Color.argb(color3a, color3r, color3g, color3b);
    }

}
