package com.ataulm.basic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class EpisodeNextUpViewHolder extends NextUpViewHolder {

    static EpisodeNextUpViewHolder inflate(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_next_up_item_episode, parent, false);
        return new EpisodeNextUpViewHolder(view);
    }

    private EpisodeNextUpViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(NextUpItem item) {
        Episode episode = (Episode) item.get();
        ((TextView) itemView).setText(episode.getName());
    }

}
