package com.ataulm.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StartScanningActivity extends Activity {

    private TextView scanDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scanning);
        scanDataTextView = (TextView) findViewById(R.id.start_scanning_text_scan_data);
    }

    public void startScanning(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String scanData = (scanningResult != null) ? scanningResult.getContents() : "";

        if (scanData == null || scanData.isEmpty()) {
            scanDataTextView.setText("Scan complete, no data");
        } else {
            scanDataTextView.setText(scanData);
        }
    }

    public void openActivityWithFragment(View view) {
        startActivity(new Intent(this, StartScanningFromFragmentActivity.class));
    }

}
