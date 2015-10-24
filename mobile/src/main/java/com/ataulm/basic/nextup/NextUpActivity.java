package com.ataulm.basic.nextup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import com.ataulm.basic.KeyDownToaster;
import com.ataulm.basic.R;
import com.ataulm.basic.Toaster;

public class NextUpActivity extends AppCompatActivity implements EpisodeClickListener {

    private WatchedRepository watchedRepository;
    private NextUpAdapter adapter;
    private RecyclerView nextUpRecyclerView;
    private KeyDownToaster keyDownToaster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_up);

        setTitle("Wutson");
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        setSupportActionBar(toolbar);

        keyDownToaster = new KeyDownToaster(this);

        watchedRepository = WatchedRepository.newInstance(this);

        nextUpRecyclerView = ((RecyclerView) findViewById(R.id.next_up_list));
        nextUpRecyclerView.setLayoutManager(new LinearLayoutManager(this) {

            @Override
            public void addView(View child) {
                super.addView(child);
                // This doesn't work so hot because often the view to which you want to scroll to isn't inflated
                child.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent event) {
                        if (event.getAction() != KeyEvent.ACTION_DOWN) {
                            return false;
                        }

                        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_NAVIGATE_PREVIOUS) {
                            scrollToPosition(getPosition(view) - 2);
                        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT || keyCode == KeyEvent.KEYCODE_TAB) {
                            scrollToPosition(getPosition(view) + 2);
                        }

                        if (keyCode == KeyEvent.KEYCODE_TAB && getPosition(view) == getItemCount() - 1) {
                            // TODO: maybe display dialog here instead:
                            // "Focus on AppBar"
                            // "Scroll to top of list"
                            // "Cancel"
                            scrollToPosition(0);
                        }
                        return false;
                    }

                });
            }

        });

        adapter = new NextUpAdapter(NextUp.MOCK_DATA, watchedRepository, this);
        nextUpRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next_up, menu);
        return true;
    }

    @Override
    public void onClick(Episode episode) {
        Toaster.display(this, "open details for " + episode.getName());
    }

    @Override
    public void onClickToggleWatched(Episode episode) {
        watchedRepository.toggledWatched(episode);
        adapter.notifyDataSetChanged();

        if (watchedRepository.isWatched(episode)) {
            nextUpRecyclerView.announceForAccessibility("Marked " + episode.getName() + " as watched.");
        } else {
            nextUpRecyclerView.announceForAccessibility("Marked " + episode.getName() + " as not watched.");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyDownToaster.onKeyDown(keyCode, event);
    }

}
