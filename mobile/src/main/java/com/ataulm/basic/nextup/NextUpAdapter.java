package com.ataulm.basic.nextup;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

class NextUpAdapter extends RecyclerView.Adapter<NextUpViewHolder> {

    private final List<NextUpItem> data;
    private final WatchStatusProvider watchStatusProvider;
    private final EpisodeClickListener clickListener;

    public NextUpAdapter(List<NextUpItem> data, WatchStatusProvider watchStatusProvider, EpisodeClickListener clickListener) {
        this.data = data;
        this.watchStatusProvider = watchStatusProvider;
        this.clickListener = clickListener;
        super.setHasStableIds(true);
    }

    @Override
    public final void setHasStableIds(boolean hasStableIds) {
        throw new RuntimeException("This adapter has stable IDs and should not be overridden");
    }

    @Override
    public long getItemId(int position) {
        // as it's a static mock list with dummy data, position is the stable ID, not for production
        return position;
    }

    @Override
    public NextUpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.HEADER.ordinal()) {
            return HeaderNextUpViewHolder.inflate(parent);
        } else if (viewType == ViewType.EPISODE.ordinal()) {
            return EpisodeNextUpViewHolder.inflate(parent, watchStatusProvider, clickListener);
        } else {
            throw new IllegalArgumentException("Unknown viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(NextUpViewHolder holder, int position) {
        NextUpItem nextUpItem = data.get(position);
        holder.bind(nextUpItem);
    }

    @Override
    public int getItemViewType(int position) {
        NextUpItem nextUpItem = data.get(position);
        return nextUpItem.viewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
