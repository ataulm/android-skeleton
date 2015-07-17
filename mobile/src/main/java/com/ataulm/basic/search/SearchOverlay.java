package com.ataulm.basic.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ataulm.basic.R;

public class SearchOverlay extends FrameLayout {

    private EditText inputEditText;
    private RecyclerView searchSuggestionsRecyclerView;
    private SearchSuggestionAdapter suggestionsAdapter;

    public SearchOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.view_search_overlay, this);
        initialiseViews();
    }

    private void initialiseViews() {
        View backButton = findViewById(R.id.search_overlay_dismiss);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSearchOverlay();
            }
        });
        inputEditText = (EditText) findViewById(R.id.search_overlay_input);
        View clearTextButton = findViewById(R.id.search_overlay_clear_text);
        clearTextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQuery();
            }
        });
        searchSuggestionsRecyclerView = (RecyclerView) findViewById(R.id.search_overlay_list_suggestions);
        searchSuggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void update(SearchSuggestions searchSuggestions) {
        if (searchSuggestionsRecyclerView.getAdapter() == null) {
            suggestionsAdapter = new SearchSuggestionAdapter(layoutInflater());
            suggestionsAdapter.update(searchSuggestions);
            searchSuggestionsRecyclerView.setAdapter(suggestionsAdapter);
        } else {
            suggestionsAdapter.update(searchSuggestions);
        }
    }

    private LayoutInflater layoutInflater() {
        return LayoutInflater.from(getContext());
    }

    private void dismissSearchOverlay() {
        setVisibility(GONE);
        clearQuery();
    }

    private void clearQuery() {
        inputEditText.setText(null);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            inputEditText.requestFocus();
        }
    }

}
