package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ataulm.rv.SpacesItemDecoration;

public class BravoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Example.BRAVO.getTitle());
        setContentView(R.layout.activity_bravo);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpacesItemDecoration.newInstance(itemSpacing(), itemSpacing(), 1));
        recyclerView.setAdapter(
                new AdventureTimeSeasonOneAdapter(
                        new AdventureTimeSeasonOneAdapter.ViewHolder.Listener() {

                            @Override
                            public void onClick(Episode episode) {
                                Toaster.display(BravoActivity.this, "click: " + episode.getTitle());
                            }

                        }
                )
        );
    }

    private int itemSpacing() {
        return getResources().getDimensionPixelSize(R.dimen.bravo_item_spacing);
    }

    public static class AdventureTimeSeasonOneAdapter extends RecyclerView.Adapter<AdventureTimeSeasonOneAdapter.ViewHolder> {

        private final ViewHolder.Listener listener;

        public AdventureTimeSeasonOneAdapter(ViewHolder.Listener listener) {
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolder.inflate(parent, listener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Episode episode = Episode.values()[position];
            holder.bind(episode);
        }

        @Override
        public int getItemCount() {
            return Episode.values().length;
        }

        static final class ViewHolder extends RecyclerView.ViewHolder {

            private final Listener listener;
            private final ImageView titleImageView;
            private final TextView titleTextView;
            private final TextView descriptionTextView;

            static ViewHolder inflate(ViewGroup parent, Listener listener) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.view_item_bravo, parent, false);
                ImageView titleImageView = (ImageView) view.findViewById(R.id.item_bravo_image_title);
                TextView titleTextView = (TextView) view.findViewById(R.id.item_bravo_text_title);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.item_bravo_text_description);
                return new ViewHolder(view, listener, titleImageView, titleTextView, descriptionTextView);
            }

            private ViewHolder(View itemView, Listener listener, ImageView titleImageView, TextView titleTextView, TextView descriptionTextView) {
                super(itemView);
                this.listener = listener;
                this.titleImageView = titleImageView;
                this.titleTextView = titleTextView;
                this.descriptionTextView = descriptionTextView;
            }

            public void bind(final Episode episode) {
                titleImageView.setImageResource(episode.getTitleCard());
                titleTextView.setText(episode.getTitle());
                descriptionTextView.setText(episode.getDescription());
//                itemView.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        listener.onClick(episode);
//                    }
//
//                });
            }

            interface Listener {

                void onClick(Episode episode);

            }

        }

    }

}
