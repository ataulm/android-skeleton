package com.ataulm.basic;

import java.util.Arrays;
import java.util.List;

class DummyBookProvider implements BookProvider {

    private static final List<String> DUMMY = Arrays.asList(
            "Harry Potter", "LOTR1", "Artemis Fowl",
            "Dead Souls", "Northern Lights", "Subtle Knife",
            "Borrowers", "The Carpet People",
            "Hitchhiker's Guide", "Carrie",
            "Salem's Lot", "Clean Code", "Hungry Caterpillar");


    @Override
    public String getBook(int position) {
        return DUMMY.get(position);
    }

    @Override
    public int size() {
        return DUMMY.size();
    }

}
