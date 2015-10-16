package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class AdventureTimeSeasonOneAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {

    private final EpisodeClickListener episodeClickListener;
    private final StarChecker starChecker;

    public AdventureTimeSeasonOneAdapter(EpisodeClickListener episodeClickListener, StarChecker starChecker) {
        this.episodeClickListener = episodeClickListener;
        this.starChecker = starChecker;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EpisodeViewHolder.inflate(parent, episodeClickListener);
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        Episode episode = Episode.values()[position];
        holder.bind(episode, starChecker.isStarred(episode));
    }

    @Override
    public int getItemCount() {
        return Episode.values().length;
    }

}
