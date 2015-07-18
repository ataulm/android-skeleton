package com.ataulm.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ataulm.basic.search.SearchOverlay;
import com.ataulm.basic.search.SearchSuggestion;
import com.ataulm.basic.search.SearchSuggestions;

import java.util.Arrays;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    private SearchOverlay searchOverlay;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        onCreateSearchOverlay();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void toast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void onCreateSearchOverlay() {
        searchOverlay = ((SearchOverlay) findViewById(R.id.search_overlay));
        searchOverlay.update(dummySearchSuggestions(), new SearchOverlay.SearchListener() {
            @Override
            public void onQueryUpdated(String query) {
                toast("update: " + query);
            }

            @Override
            public void onQuerySubmitted(String query) {
                toast("submit: " + query);
            }
        });
    }

    private SearchSuggestions dummySearchSuggestions() {
        return new ListSearchSuggestions();
    }

    private static class ListSearchSuggestions implements SearchSuggestions {

        private static List<SearchSuggestion> HISTORY_SUGGESTIONS = Arrays.asList(
                searchSuggestionFrom("Arrow", SearchSuggestion.Type.HISTORY),
                searchSuggestionFrom("Modern Family", SearchSuggestion.Type.HISTORY),
                searchSuggestionFrom("Doctor Who", SearchSuggestion.Type.HISTORY),
                searchSuggestionFrom("Flash", SearchSuggestion.Type.HISTORY)
        );

        private static List<SearchSuggestion> DOC_SUGGESTIONS = Arrays.asList(
                searchSuggestionFrom("Doctor Who", SearchSuggestion.Type.HISTORY),
                searchSuggestionFrom("Doc", SearchSuggestion.Type.API_KNOWN_RESULT),
                searchSuggestionFrom("Doc Corkie", SearchSuggestion.Type.API_KNOWN_RESULT),
                searchSuggestionFrom("Doctor", SearchSuggestion.Type.WORD_COMPLETION),
                searchSuggestionFrom("Doctors", SearchSuggestion.Type.API_KNOWN_RESULT)
        );

        private static SearchSuggestion searchSuggestionFrom(final String name, final SearchSuggestion.Type type) {
            return new SearchSuggestion() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public Type getType() {
                    return type;
                }
            };
        }

        @Override
        public SearchSuggestion getItem(int position) {
            return HISTORY_SUGGESTIONS.get(position);
        }

        @Override
        public int getItemCount() {
            return HISTORY_SUGGESTIONS.size();
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
