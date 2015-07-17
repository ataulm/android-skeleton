package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ataulm.basic.search.SearchOverlay;
import com.ataulm.basic.search.SearchSuggestion;
import com.ataulm.basic.search.SearchSuggestions;

import java.util.Arrays;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    private SearchOverlay searchOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        onCreateSearchOverlay();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void onCreateSearchOverlay() {
        searchOverlay = ((SearchOverlay) findViewById(R.id.search_overlay));
        searchOverlay.update(dummySearchSuggestions());
    }

    private SearchSuggestions dummySearchSuggestions() {
        return new ListSearchSuggestions();
    }

    private static class ListSearchSuggestions implements SearchSuggestions {

        private static List<SearchSuggestion> SUGGESTIONS = Arrays.asList(
                searchSuggestionFrom("foo"),
                searchSuggestionFrom("bar"),
                searchSuggestionFrom("what"),
                searchSuggestionFrom("ladida"),
                searchSuggestionFrom("blah"),
                searchSuggestionFrom("mlem"),
                searchSuggestionFrom("lorem")
        );

        private static SearchSuggestion searchSuggestionFrom(final String name) {
            return new SearchSuggestion() {
                @Override
                public String getName() {
                    return name;
                }
            };
        }

        @Override
        public SearchSuggestion getItem(int position) {
            return SUGGESTIONS.get(position);
        }

        @Override
        public int getItemCount() {
            return SUGGESTIONS.size();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.my_activity_search) {
            searchOverlay.setVisibility(View.VISIBLE);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
