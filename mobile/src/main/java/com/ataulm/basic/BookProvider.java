package com.ataulm.basic;

interface BookProvider {
    Book getBook(int position);
    int size();
}
