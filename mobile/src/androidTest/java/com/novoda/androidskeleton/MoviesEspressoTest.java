package com.novoda.androidskeleton;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * These tests don't make sense. They are here only to show that MovieFixtures appears accessible but DOES NOT COMPILE from mobile/androidTest test classes.
 */
@RunWith(AndroidJUnit4.class)
public class MoviesEspressoTest {

    @Rule
    public ActivityTestRule<MyActivity> activityRule = new ActivityTestRule<>(MyActivity.class);

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
