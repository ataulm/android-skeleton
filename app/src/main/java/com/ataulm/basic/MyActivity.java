package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MyActivity extends Activity {

    private static final String LINE_SPACING_PATTERN = "Line spacing extra: %dsp";
    private static final String FONT_SIZE_PATTERN = "Font size: %dsp";
    private static final int DEFAULT_TEXT_SIZE_SP = 14;
    private static final int DEFAULT_LINE_SPACING_EXTRA_PX = 0;

    TextView testTextView;
    TextView fontSizeTextView;
    TextView lineSpacingTextView;
    CheckBox enableLinesCheckBox;
    RulerView rulerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        testTextView = (TextView) findViewById(R.id.test_textview);
        testTextView.setText("The quick brown fox jumps over the lazy dog");
        testTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_SP);
        testTextView.setLineSpacing(DEFAULT_LINE_SPACING_EXTRA_PX, 1);

        fontSizeTextView = (TextView) findViewById(R.id.font_size_textview);
        fontSizeTextView.setText(String.format(FONT_SIZE_PATTERN, DEFAULT_TEXT_SIZE_SP));

        lineSpacingTextView = (TextView) findViewById(R.id.line_spacing_textview);
        lineSpacingTextView.setText(String.format(LINE_SPACING_PATTERN, 0));

        enableLinesCheckBox = (CheckBox) findViewById(R.id.enable_lines_checkbox);
        rulerView = (RulerView) findViewById(R.id.ruler);
        rulerView.setVisibility(enableLinesCheckBox.isChecked() ? View.VISIBLE : View.INVISIBLE);

        enableLinesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rulerView.setVisibility(enableLinesCheckBox.isChecked() ? View.VISIBLE : View.INVISIBLE);
            }

        });
    }

}
