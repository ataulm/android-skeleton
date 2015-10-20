package com.ataulm.basic.nextup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ataulm.basic.AccessibilityChecker;
import com.ataulm.basic.R;

final class HeaderNextUpViewHolder extends NextUpViewHolder {

    private final AccessibilityChecker accessibilityChecker;

    static HeaderNextUpViewHolder inflate(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_next_up_item_header, parent, false);
        AccessibilityChecker accessibilityChecker = AccessibilityChecker.newInstance(parent.getContext());
        return new HeaderNextUpViewHolder(view, accessibilityChecker);
    }

    private HeaderNextUpViewHolder(View itemView, AccessibilityChecker accessibilityChecker) {
        super(itemView);
        this.accessibilityChecker = accessibilityChecker;
    }

    @Override
    public void bind(NextUpItem item) {
        String header = (String) item.get();
        ((TextView) itemView).setText(header);
        itemView.setContentDescription(header + " heading");

        if (accessibilityChecker.isSpokenFeedbackEnabled()) {
            itemView.setFocusable(true);
        } else {
            itemView.setFocusable(false);
        }
    }

}
