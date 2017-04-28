package com.novoda.androidskeleton;

public class MovieFixtures {

    private String name;

    public static MovieFixtures movie() {
        return new MovieFixtures("Edward Scissorhands");
    }

    private MovieFixtures(String defaultName) {
        this.name = defaultName;
    }

    public MovieFixtures withName(String name) {
        this.name = name;
        return this;
    }

    public Movie build() {
        return new Movie(name);
    }

}
