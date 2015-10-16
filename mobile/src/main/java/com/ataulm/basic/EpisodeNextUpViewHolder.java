package com.ataulm.basic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class EpisodeNextUpViewHolder extends NextUpViewHolder {

    private final WatchStatusProvider watchStatusProvider;
    private final EpisodeClickListener clickListener;

    static EpisodeNextUpViewHolder inflate(ViewGroup parent, WatchStatusProvider watchStatusProvider, EpisodeClickListener clickListener) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_next_up_item_episode, parent, false);
        return new EpisodeNextUpViewHolder(view, watchStatusProvider, clickListener);
    }

    private EpisodeNextUpViewHolder(View itemView, WatchStatusProvider watchStatusProvider, EpisodeClickListener clickListener) {
        super(itemView);
        this.watchStatusProvider = watchStatusProvider;
        this.clickListener = clickListener;
    }

    @Override
    public void bind(NextUpItem item) {
        final Episode episode = (Episode) item.get();
        ((TextView) itemView).setText(episode.getName());

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(episode);
            }

        });
    }

}
