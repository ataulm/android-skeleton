package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class UhOhActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uh_oh);
        TextView view = (TextView) findViewById(R.id.uhoh_text);

        view.setText("uh oh got an exception");
        Log.e("RUHROH", "in ze uh oh activity");
    }

}
