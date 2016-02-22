package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ataulm.basic.model.Line;
import com.ataulm.basic.model.LondonUnderground;
import com.google.gson.Gson;

public class LondonUndergroundActivity extends AppCompatActivity {

    private static final int VICTORIA_LINE = 8; // lol so gross
    private final LondonUndergroundRemoteApi api = new LondonUndergroundRemoteApi();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_london_underground);

        // this would typically be done somewhere else
        String jsonResponse = api.getUndergroundLinesJson();
        LondonUnderground londonUnderground = new Gson().fromJson(jsonResponse, LondonUnderground.class);

        Line victoriaLine = londonUnderground.lines.get(VICTORIA_LINE);
        display(victoriaLine);
    }

    private void display(Line line) {
        // TODO: display victoria line map
    }

}
