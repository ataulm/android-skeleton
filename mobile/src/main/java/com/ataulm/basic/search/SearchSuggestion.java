package com.ataulm.basic.search;

public interface SearchSuggestion {

    String getName();

    Type getType();

    enum Type {
        HISTORY,
        WORD_COMPLETION,
        API_KNOWN_RESULT
    }

}
