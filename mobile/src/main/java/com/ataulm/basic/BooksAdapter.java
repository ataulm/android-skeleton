package com.ataulm.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class BooksAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private final LayoutInflater layoutInflater;
    private final BookProvider bookProvider;

    private final BookInteractionsListener listener;

    BooksAdapter(LayoutInflater layoutInflater, BookProvider bookProvider, BookInteractionsListener listener) {
        this.layoutInflater = layoutInflater;
        this.bookProvider = bookProvider;
        this.listener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BookViewHolder.inflate(parent, layoutInflater);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = bookProvider.getBook(position);
        holder.bind(book, listener);
    }

    @Override
    public int getItemCount() {
        return bookProvider.size();
    }

}
