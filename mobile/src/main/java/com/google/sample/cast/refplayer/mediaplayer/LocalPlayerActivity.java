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

    private static final String TAG = "LocalPlayerActivity";
    private NoPlayerView mNoPlayerView;
    private TextView mTitleView;
    private TextView mDescriptionView;
    private View mContainer;
    private ImageView mCoverArt;
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaItem mSelectedMedia;
    private TextView mAuthorView;
    private ImageButton mPlayCircle;
    private NoPlayer mNoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        mNoPlayer = new PlayerBuilder().withPriority(PlayerType.EXO_PLAYER).build(this);

        mAquery = new AQuery(this);
        loadViews();
        mNoPlayer.attach(mNoPlayerView);
        // see what we need to play and where
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSelectedMedia = MediaItem.fromBundle(getIntent().getBundleExtra("media"));
            setupActionBar();
            boolean shouldStartPlayback = bundle.getBoolean("shouldStart");
            int startPosition = bundle.getInt("startPosition", 0);

            mNoPlayer.getListeners().addPreparedListener(new NoPlayer.PreparedListener() {
                @Override
                public void onPrepared(PlayerState playerState) {
                    prepared = true;
                    mNoPlayer.play();
                }
            });

            Log.d(TAG, "Setting url of the VideoView to: " + mSelectedMedia.getUrl());
            if (shouldStartPlayback) {
                // this will be the case only if we are coming from the
                // CastControllerActivity by disconnecting from a device
                if (startPosition > 0) {
                    mNoPlayer.seekTo(VideoPosition.fromMillis(startPosition));
                }
                prepareNoPlayerWithVideo();
            }
            if (mTitleView != null) {
                updateMetadata(true);
            }
        }
    }

    private void prepareNoPlayerWithVideo() {
        if (ContentType.DASH.name().equalsIgnoreCase(mSelectedMedia.getContentType())) {
            mNoPlayer.loadVideo(Uri.parse(mSelectedMedia.getUrl()), ContentType.DASH);
        } else {
            mNoPlayer.loadVideo(Uri.parse(mSelectedMedia.getUrl()), ContentType.H264);
        }
    }

    private boolean prepared;

    private void togglePlayback() {
        if (!prepared) {
            prepareNoPlayerWithVideo();
            return;
        }

        if (mNoPlayer.isPlaying()) {
            mNoPlayer.pause();
        } else {
            mNoPlayer.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNoPlayer.pause();
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
            mContainer.setBackgroundColor(getResources().getColor(R.color.black));

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
            mContainer.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void updateMetadata(boolean visible) {
        Point displaySize;
        if (!visible) {
            mDescriptionView.setVisibility(GONE);
            mTitleView.setVisibility(GONE);
            mAuthorView.setVisibility(GONE);
            displaySize = Utils.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(
                    displaySize.x,
                    displaySize.y + getSupportActionBar().getHeight()
            );
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mNoPlayerView.setLayoutParams(lp);
            mNoPlayerView.invalidate();
        } else {
            mDescriptionView.setText(mSelectedMedia.getSubTitle());
            mTitleView.setText(mSelectedMedia.getTitle());
            mAuthorView.setText(mSelectedMedia.getStudio());
            mDescriptionView.setVisibility(View.VISIBLE);
            mTitleView.setVisibility(View.VISIBLE);
            mAuthorView.setVisibility(View.VISIBLE);
            displaySize = Utils.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(
                    displaySize.x,
                    (int) (displaySize.x * mAspectRatio)
            );
            lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
            mNoPlayerView.setLayoutParams(lp);
            mNoPlayerView.invalidate();
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
        Intent intent;
        if (item.getItemId() == R.id.action_settings) {
            intent = new Intent(LocalPlayerActivity.this, CastPreference.class);
            startActivity(intent);
        } else if (item.getItemId() == android.R.id.home) {
            ActivityCompat.finishAfterTransition(this);
        }
        return true;
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mSelectedMedia.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadViews() {
        mNoPlayerView = (NoPlayerView) findViewById(R.id.videoView1);
        mTitleView = (TextView) findViewById(R.id.textView1);
        mDescriptionView = (TextView) findViewById(R.id.textView2);
        mDescriptionView.setMovementMethod(new ScrollingMovementMethod());
        mAuthorView = (TextView) findViewById(R.id.textView3);
        TextView mStartText = (TextView) findViewById(R.id.startText);
        mStartText.setText(Utils.formatMillis(0));
        mContainer = findViewById(R.id.container);
        mCoverArt = (ImageView) findViewById(R.id.coverArtView);
        ViewCompat.setTransitionName(mCoverArt, getString(R.string.transition_image));
        mPlayCircle = (ImageButton) findViewById(R.id.play_circle);
        mPlayCircle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayback();
            }
        });
    }
}
