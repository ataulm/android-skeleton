package com.ataulm.basic.nextup;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ataulm.basic.navigation.NavigationDrawerActivity;
import com.ataulm.basic.R;
import com.ataulm.basic.Toaster;
import com.ataulm.basic.navigation.NavigationDrawerView;

public class NextUpActivity extends NavigationDrawerActivity implements EpisodeClickListener {

    private WatchedRepository watchedRepository;
    private NextUpAdapter adapter;
    private RecyclerView nextUpRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_up);

        watchedRepository = WatchedRepository.newInstance(this);

        nextUpRecyclerView = ((RecyclerView) findViewById(R.id.next_up_list));
        nextUpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NextUpAdapter(NextUp.MOCK_DATA, watchedRepository, this);
        nextUpRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next_up, menu);
        return true;
    }

    @Override
    protected void syncCurrentPageWith(NavigationDrawerView navigationDrawerView) {
        // this is weird - we usually have an ENUM with NavigationDrawer items, and this
        // protected method would be something like `protected NavItem getCurrentPage()`
        // where the activity just returns the current page. I didn't do the enums for this demo though

        // this is to highlight the correct entry in the nav drawer
        navigationDrawerView.setMyShowsAsCurrentPage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != android.R.id.home) {
            Toaster.display(this, "clicked " + item.getTitle());
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            Log.e("FOO", "marked " + episode.getName() + " as watched");
            nextUpRecyclerView.announceForAccessibility("Marked " + episode.getName() + " as watched.");
        } else {
            Log.e("FOO", "marked " + episode.getName() + " as not watched");
            nextUpRecyclerView.announceForAccessibility("Marked " + episode.getName() + " as not watched.");
        }
    }

}
