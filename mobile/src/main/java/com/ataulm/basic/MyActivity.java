package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyActivity extends AppCompatActivity {

    private View button;
    private View innerAction;
    private View itemview;
    private Button enableButton;
    private Button clickListenerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        itemview = findViewById(R.id.itemview);
        innerAction = findViewById(R.id.inner_action);
        enableButton = (Button) findViewById(R.id.enableButton);
        clickListenerButton = (Button) findViewById(R.id.clickListenerButton);

        final View.OnClickListener itemViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("itemview click");
            }
        };

        final View.OnClickListener innerActionClickLIstener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("innerAction click");
            }
        };

        itemview.setOnClickListener(itemViewClickListener);
        innerAction.setOnClickListener(innerActionClickLIstener);

        clickListenerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (innerAction.hasOnClickListeners()) {
                    innerAction.setOnClickListener(null);
                    innerAction.setClickable(false);
                } else {
                    innerAction.setOnClickListener(innerActionClickLIstener);
                }

                updateLabels();
            }
        });

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemview.setEnabled(!innerAction.isEnabled());
                updateLabels();
            }
        });

        updateLabels();
    }

    private void updateLabels() {
        enableButton.setText("is enabled: " + innerAction.isEnabled());
        clickListenerButton.setText("has listener: " + innerAction.hasOnClickListeners());
    }

    private void log(String message) {
        Log.i("!!!", message);
    }

}
