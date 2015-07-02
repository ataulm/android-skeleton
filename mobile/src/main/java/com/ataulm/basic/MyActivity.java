package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

public class MyActivity extends Activity {

    private Snackbar snackbar;
    private int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final View anchor = findViewById(R.id.anchor_for_snackbar);
        anchor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (snackbar != null) {
                            snackbar.dismiss();
                        }
                        snackbar = Snackbar.make(anchor, "what what " + count++, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
        );

    }

}
