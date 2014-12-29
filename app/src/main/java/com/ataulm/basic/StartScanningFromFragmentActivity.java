package com.ataulm.basic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StartScanningFromFragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scanning_from_fragment);
    }

    public static class StartScanningFragment extends Fragment {

        private TextView scanDataTextView;
        private Button startScanButton;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_start_scanning, container, false);
            scanDataTextView = (TextView) view.findViewById(R.id.start_scanning_text_scan_data);
            startScanButton = (Button) view.findViewById(R.id.start_scanning_button_start_scan);
            startScanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(StartScanningFragment.this);
                    intentIntegrator.initiateScan();
                }
            });
            return view;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String scanData = (scanningResult != null) ? scanningResult.getContents() : "";

            if (scanData == null || scanData.isEmpty()) {
                scanDataTextView.setText("Scan complete, no data");
            } else {
                scanDataTextView.setText(scanData);
            }
        }

    }

}
