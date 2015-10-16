package com.ataulm.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mocking data during demo purposes only!
 * not a pattern used in production, nor even during scribbles.
 */
public final class NextUp {

    // http://gravityfalls.wikia.com/wiki/Season_1
    private static final List<Episode> GRAVITY_FALLS = Arrays.asList(
            new Episode("Tourist Trapped", R.drawable.gf_01_01, "S01E01", "15 June 2012"),
            new Episode("The Legend of the Gobblewonker", R.drawable.gf_01_02, "S01E02", "29 June 2012"),
            new Episode("Headhunters", R.drawable.gf_01_03, "S01E03", "30 June 2012")
    );

    // http://thenewsroom.wikia.com/wiki/Season_3
    private static final List<Episode> NEWSROOM = Arrays.asList(
            new Episode("Oh Shenandoah", R.drawable.nr_03_05, "S03E05", "7 December 2014"),
            new Episode("What Kind of Day Has It Been", R.drawable.nr_03_06, "S03E06", "14 December 2014")
    );

    // http://steven-universe.wikia.com/wiki/Episode_Guide
    private static final List<Episode> STEVEN_UNIVERSE = Arrays.asList(
            new Episode("Giant Woman", R.drawable.su_01_12, "S01E12", "24 February 2014"),
            new Episode("So Many Birthdays", R.drawable.su_01_13, "S01E13", "3 March 2014"),
            new Episode("Lars and the Cool Kids", R.drawable.su_01_14, "S01E14", "10 March 2014")
    );

    // http://daria.wikia.com/wiki/Episode_Guide
    private static final List<Episode> DARIA = Arrays.asList(
            new Episode("Esteemsters", R.drawable.d_01_01, "S01E01", "3 March 1997"),
            new Episode("The Invitation", R.drawable.d_01_02, "S01E02", "10 March 1997"),
            new Episode("College Bored", R.drawable.d_01_03, "S01E03", "17 March 1997")
    );

    // http://brooklyn99.wikia.com/wiki/Season_Three
    private static final List<Episode> BROOKLYN_NINE_NINE = Arrays.asList(
            new Episode("New Captain", R.drawable.b_03_01, "S03E01", "27 September 2015"),
            new Episode("The Funeral", R.drawable.placeholder, "S03E02", "4 October 2015"),
            new Episode("Boyle’s Hunch", R.drawable.placeholder, "S03E03", "11 October 2015")
    );

    private static List<NextUpItem> collateItems() {
        List<NextUpItem> list = new ArrayList<>();
        list.add(header("Brooklyn Nine-Nine"));
        list.addAll(episodes(BROOKLYN_NINE_NINE));
        list.add(header("Steven Universe"));
        list.addAll(episodes(STEVEN_UNIVERSE));
        list.add(header("Gravity Falls"));
        list.addAll(episodes(GRAVITY_FALLS));
        list.add(header("The Newsroom"));
        list.addAll(episodes(NEWSROOM));
        list.add(header("Daria"));
        list.addAll(episodes(DARIA));
        return list;
    }

    private static NextUpItem<String> header(final String title) {
        return new NextUpItem<String>() {
            @Override
            public ViewType viewType() {
                return ViewType.HEADER;
            }

            @Override
            public String get() {
                return title;
            }
        };
    }

    private static List<NextUpItem> episodes(List<Episode> episodes) {
        List<NextUpItem> nextUpItems = new ArrayList<>();
        for (final Episode episode : episodes) {
            nextUpItems.add(new NextUpItem<Episode>() {
                @Override
                public ViewType viewType() {
                    return ViewType.EPISODE;
                }

                @Override
                public Episode get() {
                    return episode;
                }
            });
        }
        return nextUpItems;
    }

    public static final List<NextUpItem> MOCK_DATA = collateItems();

    private NextUp() {
        // just used as a data holder for mock data
    }

}
