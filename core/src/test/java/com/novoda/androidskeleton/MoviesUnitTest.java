package com.novoda.androidskeleton;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * These tests don't make sense. They are here only to show that MovieFixtures is accessible and usable from core/test test classes.
 */
public class MoviesUnitTest {

    @Test
    public void defaultMovieName() {
        Movie fooMovie = MovieFixtures.movie().build();

        assertThat(fooMovie.name).isEqualTo("Edward Scissorhands");
    }

    @Test
    public void customMovieName() {
        String fooTitle = "foo";
        Movie fooMovie = MovieFixtures.movie().withName(fooTitle).build();

        assertThat(fooMovie.name).isEqualTo(fooTitle);
    }

}