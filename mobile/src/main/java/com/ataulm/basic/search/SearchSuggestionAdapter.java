package com.ataulm.basic.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class SearchSuggestionAdapter extends RecyclerView.Adapter<SearchSuggestionViewHolder> {

    private final LayoutInflater layoutInflater;
    private SearchSuggestions searchSuggestions;

    SearchSuggestionAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void update(SearchSuggestions searchSuggestions) {
        this.searchSuggestions = searchSuggestions;
        notifyDataSetChanged();
    }

    @Override
    public SearchSuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SearchSuggestionViewHolder.inflate(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(SearchSuggestionViewHolder holder, int position) {
        SearchSuggestion item = searchSuggestions.getItem(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        if (searchSuggestions == null) {
            return 0;
        }
        return searchSuggestions.getItemCount();
    }

}
