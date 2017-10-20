/*
 * Copyright (C) 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cast.refplayer.mediaplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.sample.cast.refplayer.R;
import com.google.sample.cast.refplayer.settings.CastPreference;
import com.google.sample.cast.refplayer.utils.MediaItem;
import com.google.sample.cast.refplayer.utils.Utils;
import com.novoda.noplayer.ContentType;
import com.novoda.noplayer.NoPlayer;
import com.novoda.noplayer.NoPlayerView;
import com.novoda.noplayer.PlayerBuilder;
import com.novoda.noplayer.PlayerState;
import com.novoda.noplayer.PlayerType;
import com.novoda.noplayer.model.VideoPosition;

import static android.view.View.GONE;

/**
 * Activity for the local media player.
 */
public class LocalPlayerActivity extends AppCompatActivity {

    public static final String MEDIA = "media";
    public static final String SHOULD_START_PLAYBACK = "shouldStart";
    private static final String START_POSITION = "startPosition";
    private static final String TAG = "LocalPlayerActivity";
    private static final float mAspectRatio = 72f / 128;

    private TextView titleView;
    private TextView descriptionView;
    private View rootView;
    private MediaItem selectedMedia;
    private TextView authorView;
    private NoPlayerView noPlayerView;
    private NoPlayer noPlayer;
    private ImageButton playButton;
    private ImageView coverArtImageView;
    private TextView elapsedTimeTextView;
    private TextView durationTextView;
    private SeekBar seekBar;
    private View playerControlsBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        noPlayerView = findViewById(R.id.player_view);
        noPlayer = new PlayerBuilder().withPriority(PlayerType.EXO_PLAYER).build(this);
        noPlayer.attach(noPlayerView);

        titleView = findViewById(R.id.player_text_title);
        descriptionView = findViewById(R.id.player_text_description);
        descriptionView.setMovementMethod(new ScrollingMovementMethod());
        authorView = findViewById(R.id.player_text_author);

        durationTextView = findViewById(R.id.player_text_duration);
        elapsedTimeTextView = findViewById(R.id.player_text_elapsed_time);
        elapsedTimeTextView.setText(Utils.formatMillis(0));

        rootView = findViewById(R.id.player_root_view);
        coverArtImageView = findViewById(R.id.player_cover_art_view);
        ViewCompat.setTransitionName(coverArtImageView, getString(R.string.transition_image));
        seekBar = findViewById(R.id.player_seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                VideoPosition videoPosition = VideoPosition.fromSeconds(seekBar.getProgress());
                noPlayer.seekTo(videoPosition);
            }
        });
        playerControlsBottomBar = findViewById(R.id.player_controls_bottom_bar);
        View pauseButton = findViewById(R.id.player_button_pause);
        pauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                noPlayer.pause();
            }
        });
        playButton = findViewById(R.id.player_button_play);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            throw new IllegalStateException("LocalPlayerActivity should not be able to exist without passing bundle");
        }
        selectedMedia = MediaItem.fromBundle(getIntent().getBundleExtra(MEDIA));
        seekBar.setMax(selectedMedia.getDuration());
        durationTextView.setText(Utils.formatSeconds(selectedMedia.getDuration()));
        int startPosition = bundle.getInt(START_POSITION, 0);
        boolean shouldStartPlayback = bundle.getBoolean(SHOULD_START_PLAYBACK);
        prepareMediaPlayback(selectedMedia, startPosition, shouldStartPlayback);
    }

    private void prepareMediaPlayback(MediaItem media, int startPosition, boolean shouldStartPlayback) {
        setupActionBar();

        noPlayer.getListeners().addHeartbeatCallback(new NoPlayer.HeartbeatCallback() {
            @Override
            public void onBeat(NoPlayer player) {
                VideoPosition playheadPosition = noPlayer.getPlayheadPosition();
                int progress = playheadPosition.inImpreciseSeconds();
                seekBar.setProgress(progress);
                elapsedTimeTextView.setText(Utils.formatMillis(playheadPosition.inImpreciseMillis()));
            }
        });

        noPlayer.getListeners().addStateChangedListener(new NoPlayer.StateChangedListener() {
            @Override
            public void onVideoPlaying() {
                playButton.setVisibility(GONE);
                playerControlsBottomBar.setVisibility(View.VISIBLE);
                coverArtImageView.setVisibility(GONE);
            }

            @Override
            public void onVideoPaused() {
                playButton.setVisibility(View.VISIBLE);
                playerControlsBottomBar.setVisibility(View.GONE);
            }

            @Override
            public void onVideoStopped() {
                prepared = false;
                playButton.setVisibility(View.VISIBLE);
                playerControlsBottomBar.setVisibility(View.GONE);
                coverArtImageView.setVisibility(View.VISIBLE);
                new AQuery(LocalPlayerActivity.this).id(coverArtImageView).image(selectedMedia.getImage(0),
                        true, true, 0, R.drawable.default_video, null, 0, mAspectRatio);

            }
        });

        noPlayer.getListeners().addPreparedListener(new NoPlayer.PreparedListener() {
            @Override
            public void onPrepared(PlayerState playerState) {
                prepared = true;
                noPlayer.play();
            }
        });

        Log.d(TAG, "Setting url of the VideoView to: " + media.getUrl());
        if (shouldStartPlayback) {
            // this will be the case only if we are coming from the
            // CastControllerActivity by disconnecting from a device
            if (startPosition > 0) {
                noPlayer.seekTo(VideoPosition.fromMillis(startPosition));
            }
            prepareNoPlayerWithVideo();
        }
        if (titleView != null) {
            updateMetadata(true);
        }
    }

    private boolean prepared;

    private void playVideo() {
        if (!prepared) {
            prepareNoPlayerWithVideo();
            return;
        }
        noPlayer.play();
    }

    private void prepareNoPlayerWithVideo() {
        if (ContentType.DASH.name().equalsIgnoreCase(selectedMedia.getContentType())) {
            noPlayer.loadVideo(Uri.parse(selectedMedia.getUrl()), ContentType.DASH);
        } else {
            noPlayer.loadVideo(Uri.parse(selectedMedia.getUrl()), ContentType.H264);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (noPlayer.isPlaying()) {
            noPlayer.pause();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getSupportActionBar().show();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
            updateMetadata(false);
            rootView.setBackgroundColor(getResources().getColor(R.color.black));

        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
            );
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            updateMetadata(true);
            rootView.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void updateMetadata(boolean visible) {
        if (!visible) {
            descriptionView.setVisibility(GONE);
            titleView.setVisibility(GONE);
            authorView.setVisibility(GONE);
            Point displaySize = Utils.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(
                    displaySize.x,
                    displaySize.y + getSupportActionBar().getHeight()
            );
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            noPlayerView.setLayoutParams(lp);
            noPlayerView.invalidate();
        } else {
            descriptionView.setText(selectedMedia.getSubTitle());
            titleView.setText(selectedMedia.getTitle());
            authorView.setText(selectedMedia.getStudio());
            descriptionView.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
            authorView.setVisibility(View.VISIBLE);
            Point displaySize = Utils.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(
                    displaySize.x,
                    (int) (displaySize.x * mAspectRatio)
            );
            lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
            noPlayerView.setLayoutParams(lp);
            noPlayerView.invalidate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(LocalPlayerActivity.this, CastPreference.class);
                startActivity(intent);
                return true;
            default:
                throw new IllegalArgumentException("unknown menu item: " + item.getTitle());
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(selectedMedia.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
