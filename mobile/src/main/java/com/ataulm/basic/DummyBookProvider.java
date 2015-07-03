package com.ataulm.basic;

import java.util.Arrays;
import java.util.List;

class DummyBookProvider implements BookProvider {

    private static final List<Book> DUMMY = Arrays.asList(
            new Book("Harry Potter"), new Book("LOTR1"), new Book("Artemis Fowl"),
            new Book("Dead Souls"), new Book("Northern Lights"), new Book("Subtle Knife"),
            new Book("Borrowers"), new Book("The Carpet People"),
            new Book("Hitchhiker's Guide"), new Book("Carrie"),
            new Book("Salem's Lot"), new Book("Clean Code"), new Book("Hungry Caterpillar"));

    @Override
    public Book getBook(int position) {
        return DUMMY.get(position);
    }

    @Override
    public int size() {
        return DUMMY.size();
    }

}
