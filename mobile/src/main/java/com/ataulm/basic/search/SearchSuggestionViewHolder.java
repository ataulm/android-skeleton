package com.ataulm.basic.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ataulm.basic.R;

final class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final ImageView typeImageView;
    private final TextView nameTextView;

    static SearchSuggestionViewHolder inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.view_search_overlay_suggestion, parent, false);
        ImageView typeImageView = ((ImageView) view.findViewById(R.id.search_overlay_suggestion_image_type));
        TextView nameTextView = ((TextView) view.findViewById(R.id.search_overlay_suggestion_text_name));
        return new SearchSuggestionViewHolder(view, typeImageView, nameTextView);
    }

    private SearchSuggestionViewHolder(View itemView, ImageView typeImageView, TextView nameTextView) {
        super(itemView);
        this.itemView = itemView;
        this.typeImageView = typeImageView;
        this.nameTextView = nameTextView;
    }

    void bind(SearchSuggestion searchSuggestion) {
        if (searchSuggestion.getType() == SearchSuggestion.Type.HISTORY) {
            typeImageView.setImageResource(R.drawable.ic_search_history);
            typeImageView.setVisibility(View.VISIBLE);
        } else {
            typeImageView.setVisibility(View.GONE);
        }
        nameTextView.setText(searchSuggestion.getName());
    }

}
