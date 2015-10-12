package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

final class AlphabetViewHolder extends RecyclerView.ViewHolder {

    public static AlphabetViewHolder inflateWithParamsFrom(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_alphabet, parent, false);
        return new AlphabetViewHolder(view);
    }

    private AlphabetViewHolder(View itemView) {
        super(itemView);
    }

}
