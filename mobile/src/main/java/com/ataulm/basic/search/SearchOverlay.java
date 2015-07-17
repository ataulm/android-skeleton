package com.ataulm.basic.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ataulm.basic.R;

public class SearchOverlay extends FrameLayout {

    private EditText inputEditText;

    public SearchOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.view_search_overlay, this);
        initialiseViews();
    }

    private void initialiseViews() {
        View backButton = findViewById(R.id.search_overlay_back);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSearchOverlay();
            }
        });
        inputEditText = (EditText) findViewById(R.id.search_overlay_input);
        View clearTextButton = findViewById(R.id.search_overlay_clear_text);
        clearTextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQuery();
            }
        });
    }

    private void dismissSearchOverlay() {
        setVisibility(GONE);
        clearQuery();
    }

    private void clearQuery() {
        inputEditText.setText(null);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            inputEditText.requestFocus();
        }
    }

}
