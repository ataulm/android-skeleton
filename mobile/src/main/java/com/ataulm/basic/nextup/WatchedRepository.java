package com.ataulm.basic.nextup;

import android.content.Context;
import android.content.SharedPreferences;

import com.ataulm.basic.BuildConfig;

final class WatchedRepository implements WatchStatusProvider {

    private static final String KEY = BuildConfig.APPLICATION_ID + ".WATCHED_EPISODES";

    private final SharedPreferences sharedPreferences;

    static WatchedRepository newInstance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return new WatchedRepository(sharedPreferences);
    }

    private WatchedRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private void markWatched(Episode episode) {
        sharedPreferences.edit()
                .putBoolean(episode.getName(), true)
                .apply();
    }

    private void markNotWatched(Episode episode) {
        sharedPreferences.edit()
                .putBoolean(episode.getName(), false)
                .apply();
    }

    @Override
    public boolean isWatched(Episode episode) {
        return sharedPreferences.getBoolean(episode.getName(), false);
    }

    public void toggledWatched(Episode episode) {
        if (isWatched(episode)) {
            markNotWatched(episode);
        } else {
            markWatched(episode);
        }
    }

}
