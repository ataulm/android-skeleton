package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class BookViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleView;
    private final TextView authorView;

    public static BookViewHolder inflate(ViewGroup parent, LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.view_book, parent, false);

        TextView titleView = (TextView) view.findViewById(R.id.book_name_text);
        TextView authorView = (TextView) view.findViewById(R.id.book_author_text);

        return new BookViewHolder(view, titleView, authorView);
    }

    private BookViewHolder(View itemView, TextView titleView, TextView authorView) {
        super(itemView);
        this.titleView = titleView;
        this.authorView = authorView;
    }

    public void bind(final Book book, final BookInteractionsListener listener) {
        titleView.setText(book.getBookName());
        authorView.setText(book.getAuthorName());

        itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickBook(book.getBookName());
                    }
                }
        );

        itemView.setContentDescription(book.getBookName());
    }

}
