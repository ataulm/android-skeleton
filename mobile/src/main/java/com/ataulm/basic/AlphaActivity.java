package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AlphaActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Example.ALPHA.getTitle());
        setContentView(R.layout.activity_alpha);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toaster.display(AlphaActivity.this, "Refreshing");
            }

        });
    }

}
