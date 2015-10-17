package com.ataulm.basic.nextup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        nextUpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
