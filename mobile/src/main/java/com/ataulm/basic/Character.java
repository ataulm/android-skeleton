package com.ataulm.basic;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;

public enum Character {

    FINN("Finn", "Finn (full title: Finn the Human, or Finn Mertens, and formerly known as Pen in the original short), is the main protagonist of the series Adventure Time."),
    MARCELINE("Marceline", "Marceline, the Vampire Queen (full name: Marceline Abadeer) is one of the main characters in Adventure Time and a half-demon/half-Vampire who is over a thousand years old."),
    PRINCESS_BUBBLEGUM("Princess Bubblegum", "Bubblegum (formerly known as Princess Bubblegum, often called PB and on occasion Bonnie, Peebles, P-Bubs, or other nicknames) is a main character and the most recurring Princess on the series Adventure Time, and first appeared in the animated short."),
    ICE_KING("Ice King", "Ice King (real name Simon Petrikov) is a major character conceived, but no longer acting as, the main antagonist of Adventure Time."),
    THE_LICH("The Lich", "The Lich (formerly named the Lich King in the original pitch and \"His Hero,\" and referred to as Sweet Pig Trunks in his baby form, or just Sweet P for short) is a powerful undead being and a major antagonist in Adventure Time."),
    GUNTER("Gunter", "Gunter is the penguin that most commonly accompanies the Ice King."),
    FLAME_PRINCESS("Flame Princess", "Flame Princess (first name Phoebe and occasionally called FP) is the princess and ruler of the Fire Kingdom, as well as Finn's ex girlfriend."),
    BMO("BMO", "BMO is Finn and Jake's living video game console, portable electrical outlet, music player, roommate, camera, alarm clock, toaster, flashlight, strobe light, skateboarder, friend, soccer player, video editor, video player, tape player and Chef."),
    LADY_RAINICORN("Lady Rainicorn", "Lady Rainicorn is a female Rainicorn and is Princess Bubblegum's royal steed, best friend, and companion."),
    JAKE("Jake", "Jake (full title: Jake the Dog), the deuteragonist of Adventure Time, is a dog/shape-shifter hybrid, referred to by others as a \"magical dog,\" and Finn's constant companion, best friend, and adoptive brother."),
    LEMONGRAB("LEMONGRAB", "The Earl of Lemongrab is a recurring character in Adventure Time. He first appears in the episode \"Too Young.\""),
    LUMPY_SPACE_PRINCESS("Lumpy Space Princess", "Lumpy Space Princess (often referred to by her initials, LSP) is the princess of Lumpy Space.");

    private final String name;
    private final String description;

    Character(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @ColorInt
    public int getIconColor() {
        int position = indexOf(this);

        if (position % 3 == 0) {
            return Color.parseColor("#60B7FF");
        } else if (position % 2 == 0) {
            return Color.parseColor("#FFE76D");
        } else {
            return Color.parseColor("#FF6B80");
        }
    }

    private static int indexOf(Character character) {
        for (int i = 0; i < values().length; i++) {
            if (character == values()[i]) {
                return i;
            }
        }
        throw new IllegalStateException("Couldn't find character: " + character);
    }

}
