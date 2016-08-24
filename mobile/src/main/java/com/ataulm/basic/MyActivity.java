package com.ataulm.basic;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MyActivity extends AppCompatActivity {

    private static final int COLOR_ONE = Color.CYAN;
    private static final int COLOR_TWO = Color.GREEN;

    @ColorInt
    private int backgroundColor = COLOR_ONE;

    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        root = findViewById(R.id.root);
        syncBackgroundColor();
    }

    public void changeBackground(View view) {
        toggleBackgroundColor();
        syncBackgroundColor();
    }

    private void toggleBackgroundColor() {
        if (backgroundColor == COLOR_ONE) {
            backgroundColor = COLOR_TWO;
        } else {
            backgroundColor = COLOR_ONE;
        }
    }

    private void syncBackgroundColor() {
        root.setBackgroundColor(backgroundColor);
    }

    public void showDialog(View view) {
        final Dialog dialog = new Dialog(this, R.style.FullScreenDialog);
        SimpleDialogView inflate = (SimpleDialogView) getLayoutInflater().inflate(R.layout.view_simple_dialog, null);
        inflate.bind(new SimpleDialogView.Callback() {
            @Override
            public void onClickDismiss() {
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        dialog.show();
    }

}
