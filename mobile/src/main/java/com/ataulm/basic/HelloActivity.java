package com.ataulm.basic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends Activity {

    private boolean invisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Hello Android");
        setContentView(R.layout.activity_hello);

        final TextView instructionsTextView = (TextView) findViewById(R.id.hello_text_instructions);
        final Button helloAndroidButton = (Button) findViewById(R.id.hello_button_hello_android);
        final Drawable originalButtonBackground = helloAndroidButton.getBackground();
        final int originalTextColor = instructionsTextView.getPaint().getColor();
        final int originalButtonTextColor = helloAndroidButton.getPaint().getColor();

        findViewById(R.id.hello_button_toggle_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invisible) {
                    instructionsTextView.setTextColor(originalTextColor);
                    helloAndroidButton.setTextColor(originalButtonTextColor);
                    helloAndroidButton.setBackground(originalButtonBackground);
                    invisible = false;
                } else {
                    instructionsTextView.setTextColor(Color.TRANSPARENT);
                    helloAndroidButton.setTextColor(Color.TRANSPARENT);
                    helloAndroidButton.setBackground(null);
                    invisible = true;
                }
            }
        });

        helloAndroidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.display(HelloActivity.this, "Hello!");
            }
        });
    }

}
