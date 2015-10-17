package com.ataulm.basic.nextup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ataulm.basic.R;

final class HeaderNextUpViewHolder extends NextUpViewHolder {

    static HeaderNextUpViewHolder inflate(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_next_up_item_header, parent, false);
        return new HeaderNextUpViewHolder(view);
    }

    private HeaderNextUpViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(NextUpItem item) {
        String header = (String) item.get();
        ((TextView) itemView).setText(header);
    }

}
