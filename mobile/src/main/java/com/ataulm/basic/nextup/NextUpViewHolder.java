package com.ataulm.basic.nextup;

import android.support.v7.widget.RecyclerView;
import android.view.View;

abstract class NextUpViewHolder extends RecyclerView.ViewHolder {

    protected NextUpViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(NextUpItem item);

}
