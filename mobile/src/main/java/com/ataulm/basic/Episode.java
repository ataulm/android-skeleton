package com.ataulm.basic;

import android.support.annotation.DrawableRes;

enum Episode {

    // http://adventuretime.wikia.com/wiki/List_of_episodes
    ONE(R.drawable.ats_01, "Slumber Party Panic", "Finn and Princess Bubblegum must protect the Candy Kingdom from a horde of candy zombies they accidentally created."),
    TWO(R.drawable.ats_02, "Trouble in Lumpy Space", "Finn must travel to Lumpy Space to find a cure that will save Jake, who was accidentally bitten by Lumpy Space Princess at Princess Bubblegum's annual 'Mallow Tea Ceremony.'"),
    THREE(R.drawable.ats_03, "Prisoners of Love", "Having discovered that the Ice King kidnaps a number of princesses he sees as potential brides, Finn and Jake team with the prisoners to set everybody free."),
    FOUR(R.drawable.ats_04, "Tree Trunks", "Finn and Jake join Tree Trunks in search for the rare Crystal Gem Apple, but question whether Tree Trunks has what it takes to adventure."),
    FIVE(R.drawable.ats_05, "The Enchiridion!", "Finn and Jake go on a quest for a magical book that would prove them worthy of being righteous heroes."),
    SIX(R.drawable.ats_06, "The Jiggler", "A jiggly creature attracted by Finn's auto-tuned singing follows Finn and Jake home but soon feels ill effects of separation from its family."),
    SEVEN(R.drawable.ats_07, "Ricardio the Heart Guy", "After saving Princess Bubblegum from the Ice King yet again she offers them a party, but during the party Finn grows jealous of a suspicious heart-shaped man that is wooing with Princesss Bubblegum and sets out to prove that he's evil."),
    EIGHT(R.drawable.ats_08, "Business Time", "Finn and Jake fall victim to their own laziness when they delegate their adventuring responsibilities to a group of businessmen thawed from an iceberg."),
    NINE(R.drawable.ats_09, "My Two Favourite People", "Jake feels neglected when his plan to spend time with both Lady Rainicorn and Finn backfires."),
    TEN(R.drawable.ats_10, "Memories of Boom Boom Mountain", "Finn reflects on an upsetting experience in his past and pledges to help everyone in need, but this proves more difficult than imagined."),
    ELEVEN(R.drawable.ats_11, "Wizard", "Finn and Jake are coaxed by a skeleton man to enroll in a course for free magic powers, but are ultimately tricked into helping stop an asteroid."),
    TWELVE(R.drawable.ats_12, "Evicted!", "Finn and Jake search the land of Ooo for a new home after Marceline claims the duo's treehouse as her own."),
    THIRTEEN(R.drawable.ats_13, "City of Thieves", "Finn and Jake enter the City of Thieves to confront a thief king who has been stealing from little girls."),
    FOURTEEN(R.drawable.ats_14, "The Witch's Garden", "Jake loses his enthusiasm for adventure when a witch strips him of his powers for trespassing in her garden."),
    FIFTEEN(R.drawable.ats_15, "What is Life?", "Finn builds a pie-throwing robot to seek revenge against Jake, but the machine's conscience is conflicted by the will of the Ice King."),
    SIXTEEN(R.drawable.ats_16, "Ocean of Fear", "Jake tries to help Finn overcome his fear of the ocean."),
    SEVENTEEN(R.drawable.ats_17, "When Wedding Bells Thaw", "The Ice King suppresses his kidnapping urges to wed a suspicious bride and convinces Finn and Jake to throw him a manlorette party, but then realizes he'll have to give up his hobby of kidnapping."),
    EIGHTEEN(R.drawable.ats_18, "Dungeon", "Finn's headstrong decision to explore a dungeon to find the Crystal Eye in defiance of Jake and Princess Bubblegum's warnings puts him in great danger."),
    NINETEEN(R.drawable.ats_19, "The Duke", "Finn turns Princess Bubblegum green and bald, and faces a moral quandary—whether to confess his mistake and be hated by his friend forever, or let Princess Bubblegum wrongly accuse the Duke of Nuts instead."),
    TWENTY(R.drawable.ats_20, "Freak City", "After being transformed into a foot by a Magic Man, Finn joins forces with a band of misfits to fix the problem."),
    TWENTY_ONE(R.drawable.ats_21, "Donny", "Finn and Jake help a bullying grass ogre named Donny turn his life around, without realizing the ecological damage they may be causing in the process."),
    TWENTY_TWO(R.drawable.ats_22, "Henchman", "Finn takes the place of Marceline's henchman, but finds it hard to obey her seemingly evil commands."),
    TWENTY_THREE(R.drawable.ats_23, "What Have You done?", "Without giving good details, Princess Bubblegum tells Finn and Jake to capture the Ice King as their prisoner, but they struggle to defend their actions when they try to interrogate him."),
    TWENTY_FOUR(R.drawable.ats_24, "His Hero", "The great warrior Billy inspires Finn and Jake to practice non-violence, but the duo finds it difficult to resist their old ways."),
    TWENTY_FIVE(R.drawable.ats_25, "Gut Grinder", "When the Soft People's gold has been devoured by a culprit only known as \"The Gut Grinder,\" Finn and Jake team up to track him down.");

    @DrawableRes
    private final int titleCard;

    private final String title;

    private final String description;

    Episode(@DrawableRes int titleCard, String title, String description) {
        this.titleCard = titleCard;
        this.title = title;
        this.description = description;
    }

    @DrawableRes
    public int getTitleCard() {
        return titleCard;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
