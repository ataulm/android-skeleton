package com.novoda.androidskeleton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity {

    private TextView movieTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        movieTextView = (TextView) findViewById(R.id.movie);
    }

}
