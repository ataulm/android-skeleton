package com.ataulm.basic.nextup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.ataulm.basic.R;
import com.ataulm.basic.Toaster;

public class NextUpActivity extends Activity implements EpisodeClickListener {

    private WatchedRepository watchedRepository;
    private NextUpAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_up);

        watchedRepository = WatchedRepository.newInstance(this);

        RecyclerView nextUpRecyclerView = ((RecyclerView) findViewById(R.id.next_up_list));
        nextUpRecyclerView.setLayoutManager(new LinearLayoutManager(this) {

            @Override
            public void addView(View child) {
                super.addView(child);
                child.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent event) {
                        if (event.getAction() != KeyEvent.ACTION_DOWN) {
                            return false;
                        }

                        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            scrollToPosition(getPosition(view) - 1);
                        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                            scrollToPosition(getPosition(view) + 1);
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
    public void onClick(Episode episode) {
        Toaster.display(this, "open details for " + episode.getName());
    }

    @Override
    public void onClickToggleWatched(Episode episode) {
        watchedRepository.toggledWatched(episode);
        adapter.notifyDataSetChanged();
    }

}
