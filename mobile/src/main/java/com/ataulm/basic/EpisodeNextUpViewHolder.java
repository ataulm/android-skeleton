package com.ataulm.basic;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

final class EpisodeNextUpViewHolder extends NextUpViewHolder {

    private final WatchStatusProvider watchStatusProvider;
    private final EpisodeClickListener clickListener;
    private final ImageView titlecardImageView;
    private final TextView nameTextView;
    private final TextView numberTextView;
    private final TextView airdateTextView;
    private final ImageView watchedImageView;

    static EpisodeNextUpViewHolder inflate(ViewGroup parent, WatchStatusProvider watchStatusProvider, EpisodeClickListener clickListener) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_next_up_item_episode, parent, false);
        ImageView titlecardImageView = (ImageView) view.findViewById(R.id.next_up_episode_image_titlecard);
        TextView nameTextView = (TextView) view.findViewById(R.id.next_up_episode_text_name);
        TextView numberTextView = (TextView) view.findViewById(R.id.next_up_episode_text_number);
        TextView airdateTextView = (TextView) view.findViewById(R.id.next_up_episode_text_airdate);
        ImageView watchedImageView = (ImageView) view.findViewById(R.id.next_up_episode_image_watched);
        return new EpisodeNextUpViewHolder(view, watchStatusProvider, clickListener, titlecardImageView, nameTextView, numberTextView, airdateTextView, watchedImageView);
    }

    private EpisodeNextUpViewHolder(View itemView, WatchStatusProvider watchStatusProvider, EpisodeClickListener clickListener, ImageView titlecardImageView, TextView nameTextView, TextView numberTextView, TextView airdateTextView, ImageView watchedImageView) {
        super(itemView);
        this.watchStatusProvider = watchStatusProvider;
        this.clickListener = clickListener;
        this.titlecardImageView = titlecardImageView;
        this.nameTextView = nameTextView;
        this.numberTextView = numberTextView;
        this.airdateTextView = airdateTextView;
        this.watchedImageView = watchedImageView;
    }

    @Override
    public void bind(NextUpItem item) {
        Episode episode = (Episode) item.get();
        nameTextView.setText(episode.getName());
        numberTextView.setText(episode.getNumber());
        airdateTextView.setText(episode.getAirDate());
        titlecardImageView.setImageResource(episode.getTitlecard());
        updateWatchedButtonResource(episode);
        setWatchButtonClickListener(episode);
        setItemViewClickListener(episode);
    }

    private void updateWatchedButtonResource(Episode episode) {
        // TODO: replace with image resources
        if (watchStatusProvider.isWatched(episode)) {
            watchedImageView.setBackgroundColor(Color.BLACK);
        } else {
            watchedImageView.setBackgroundColor(Color.BLUE);
        }
    }

    private void setWatchButtonClickListener(final Episode episode) {
        watchedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClickToggleWatched(episode);
            }
        });
    }

    private void setItemViewClickListener(final Episode episode) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(episode);
            }
        });
    }

}
