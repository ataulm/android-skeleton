package com.ataulm.basic.nextup;

import android.graphics.drawable.Drawable;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.ataulm.basic.R;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class NextUpActivityTest {

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p/>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p/>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<NextUpActivity> activityRule = new ActivityTestRule<>(NextUpActivity.class);

    @Test
    public void nextUpListIsDisplayed() {
        onView(withId(R.id.next_up_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void watchedButtonBackgroundChangesOnClick() {
        onView(isEpisodeViewWithName("New Captain"))
                .check(EpisodeWatchedButtonDrawableChangedAssertion.cacheDrawablePreAction())
                .perform(clickEpisodeWatchedButton())
                .check(EpisodeWatchedButtonDrawableChangedAssertion.assertDrawableChangedPostAction());
    }

    private static TypeSafeMatcher<View> isEpisodeViewWithName(final String name) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                return isEpisodeView(view) && hasCorrectEpisodeName(view);
            }

            private boolean isEpisodeView(View view) {
                View titlecard = view.findViewById(R.id.next_up_episode_image_titlecard);
                return titlecard != null && titlecard.getParent() == view;
            }

            private boolean hasCorrectEpisodeName(View view) {
                TextView nameTextView = (TextView) view.findViewById(R.id.next_up_episode_text_name);
                return nameTextView.getText().equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("NextUp Episode item with name: " + name);
            }
        };
    }

    // The better way to do this is to ensure a fixed state at start of tests, and have two tests:
    // - one for the initial state
    // - another for clicking and asserting the drawable is correct for the opposite state
    private static class EpisodeWatchedButtonDrawableChangedAssertion {

        private static Drawable drawable;
        private static boolean started;

        public static ViewAssertion cacheDrawablePreAction() {
            return new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException e) {
                    started = true;
                    drawable = null;
                    View button = view.findViewById(R.id.next_up_episode_image_watched);
                    drawable = button.getBackground();
                }
            };
        }

        public static ViewAssertion assertDrawableChangedPostAction() {
            return new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException e) {
                    if (!started) {
                        throw new IllegalStateException("cacheDrawablePreAction must be called before assertDrawableChangedPostAction, else how can you compare?");
                    }
                    View button = view.findViewById(R.id.next_up_episode_image_watched);
                    assertThat(drawable != button.getBackground());
                }
            };
        }

    }

    private static ViewAction clickEpisodeWatchedButton() {
        CoordinatesProvider coordinatesProvider = new CoordinatesProvider() {
            @Override
            public float[] calculateCoordinates(View view) {
                View button = view.findViewById(R.id.next_up_episode_image_watched);
                int[] xy = new int[2];
                button.getLocationOnScreen(xy);

                return new float[]{xy[0] + button.getWidth() / 2, xy[1] + button.getHeight() / 2};
            }
        };
        return new GeneralClickAction(Tap.SINGLE, coordinatesProvider, Press.FINGER);
    }

}
