package com.ataulm.basic.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

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

    public void update(SearchSuggestions searchSuggestions, final SearchListener listener) {
        inputEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable text) {
                listener.onQueryUpdated(text.toString());
            }
        });

        inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (isSubmitAction(actionId, event)) {
                    listener.onQuerySubmitted(textView.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }

            private boolean isSubmitAction(int actionId, KeyEvent event) {
                return event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() != KeyEvent.KEYCODE_ENTER
                        || actionId != EditorInfo.IME_ACTION_SEARCH;
            }
        });

        if (searchSuggestionsRecyclerView.getAdapter() == null) {
            suggestionsAdapter = new SearchSuggestionAdapter(layoutInflater());
            suggestionsAdapter.update(searchSuggestions);
            searchSuggestionsRecyclerView.setAdapter(suggestionsAdapter);
        } else {
            suggestionsAdapter.update(searchSuggestions);
        }
    }

    public interface SearchListener {

        void onQueryUpdated(String query);

        void onQuerySubmitted(String query);

    }

}
