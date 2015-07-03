package com.ataulm.basic;

import java.util.Arrays;
import java.util.List;

class DummyBookProvider implements BookProvider {

    private static final List<Book> DUMMY = Arrays.asList(
            new Book("Harry Potter", "sdfasdf"), new Book("LOTR1", "sdfasdf"), new Book("Artemis Fowl", "sdfasdf"),
            new Book("Dead Souls", "sdfasdf"), new Book("Northern Lights", "sdfasdf"), new Book("Subtle Knife", "sdfasdf"),
            new Book("Borrowers", "sdfasdf"), new Book("The Carpet People", "sdfasdf"),
            new Book("Hitchhiker's Guide", "sdfasdf"), new Book("Carrie", "sdfasdf"),
            new Book("Salem's Lot", "sdfasdf"), new Book("Clean Code", "sdfasdf"), new Book("Hungry Caterpillar", "sdfasdf"));

    @Override
    public Book getBook(int position) {
        return DUMMY.get(position);
    }

    @Override
    public int size() {
        return DUMMY.size();
    }

}
