package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

final class EpisodeViewHolder extends RecyclerView.ViewHolder {

    private final EpisodeClickListener episodeClickListener;
    private final ImageView titleImageView;
    private final ImageView starButtonView;
    private final TextView titleTextView;
    private final TextView descriptionTextView;

    static EpisodeViewHolder inflate(ViewGroup parent, EpisodeClickListener episodeClickListener) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_item_bravo, parent, false);
        ImageView starButtonView = (ImageView) view.findViewById(R.id.item_bravo_button_star);
        ImageView titleImageView = (ImageView) view.findViewById(R.id.item_bravo_image_title);
        TextView titleTextView = (TextView) view.findViewById(R.id.item_bravo_text_title);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.item_bravo_text_description);
        return new EpisodeViewHolder(view, episodeClickListener, titleImageView, starButtonView, titleTextView, descriptionTextView);
    }

    private EpisodeViewHolder(View itemView, EpisodeClickListener episodeClickListener, ImageView titleImageView, ImageView starButtonView, TextView titleTextView, TextView descriptionTextView) {
        super(itemView);
        this.episodeClickListener = episodeClickListener;
        this.titleImageView = titleImageView;
        this.starButtonView = starButtonView;
        this.titleTextView = titleTextView;
        this.descriptionTextView = descriptionTextView;
    }

    public void bind(Episode episode, boolean isStarred) {
        titleImageView.setImageResource(episode.getTitleCard());
        titleTextView.setText(episode.getTitle());
        descriptionTextView.setText(episode.getDescription());
        setItemClickListenerFor(episode);
        setStarClickListenerFor(episode, isStarred);
        updateStarResources(episode, isStarred);

        itemView.setContentDescription(episode.getTitle());
    }

    private void setItemClickListenerFor(final Episode episode) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                episodeClickListener.onClick(episode);
            }
        });
    }

    private void setStarClickListenerFor(final Episode episode, final boolean wasStarred) {
        starButtonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                episodeClickListener.onClickStar(episode);
                String announcement = wasStarred
                        ? "Removed " + episode.getTitle() + " from favourites"
                        : "Added " + episode.getTitle() + " to favourites";
                starButtonView.announceForAccessibility(announcement);
            }

        });
    }

    private void updateStarResources(Episode episode, boolean isStarred) {
        if (isStarred) {
            starButtonView.setImageResource(R.drawable.ic_star_filled);
            starButtonView.setContentDescription("remove " + episode.getTitle() + " from favourites");
        } else {
            starButtonView.setImageResource(R.drawable.ic_star_empty);
            starButtonView.setContentDescription("add " + episode.getTitle() + " to favourites");
        }
    }

}
