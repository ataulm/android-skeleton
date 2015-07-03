package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class BookViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleView;

    public static BookViewHolder inflate(ViewGroup parent, LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.view_book, parent, false);
        return new BookViewHolder(view, ((TextView) view));
    }

    private BookViewHolder(View itemView, TextView titleView) {
        super(itemView);
        this.titleView = titleView;
    }

    public void bind(final String bookname, final BookInteractionsListener listener) {
        titleView.setText(bookname);
        titleView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickBook(bookname);
                    }
                }
        );
    }

}
