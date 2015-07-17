package com.ataulm.basic.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ataulm.basic.R;

final class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final TextView nameTextView;

    static SearchSuggestionViewHolder inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.view_search_overlay_suggestion, parent, false);
        TextView nameTextView = ((TextView) view.findViewById(R.id.search_overlay_suggestion_text_name));
        return new SearchSuggestionViewHolder(view, nameTextView);
    }

    private SearchSuggestionViewHolder(View itemView, TextView nameTextView) {
        super(itemView);
        this.itemView = itemView;
        this.nameTextView = nameTextView;
    }

    void bind(SearchSuggestion searchSuggestion) {
        nameTextView.setText(searchSuggestion.getName());
    }

}
