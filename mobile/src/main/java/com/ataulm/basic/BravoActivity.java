package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.ataulm.rv.SpacesItemDecoration;

import java.util.HashSet;
import java.util.Set;

public class BravoActivity extends AppCompatActivity implements EpisodeClickListener, StarChecker {

    private final Set<Episode> starredEpisodes = new HashSet<>(Episode.values().length);
    private AdventureTimeSeasonOneAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Example.BRAVO.getTitle());
        setContentView(R.layout.activity_bravo);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(SpacesItemDecoration.newInstance(itemSpacing(), itemSpacing(), 1));
        recyclerView.setAdapter(adapter = new AdventureTimeSeasonOneAdapter(this, this));
    }

    private int itemSpacing() {
        return getResources().getDimensionPixelSize(R.dimen.bravo_item_spacing);
    }

    @Override
    public void onClick(Episode episode) {
        Toaster.display(BravoActivity.this, "open details for: " + episode.getTitle());
    }

    @Override
    public void onClickStar(Episode episode) {
        if (starredEpisodes.contains(episode)) {
            starredEpisodes.remove(episode);
        } else {
            starredEpisodes.add(episode);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toaster.display(this, textRepresentationOfKeyCode(keyCode));
        return super.onKeyDown(keyCode, event);
    }

    private static String textRepresentationOfKeyCode(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return "left";
            case KeyEvent.KEYCODE_DPAD_UP:
                return "up";
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return "right";
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return "down";
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_SELECT:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
                return "select";
            default:
                return "key code: " + keyCode;
        }
    }

    @Override
    public boolean isStarred(Episode episode) {
        return starredEpisodes.contains(episode);
    }
}
