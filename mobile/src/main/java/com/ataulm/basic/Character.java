package com.ataulm.basic;

enum Character {

    // as per https://en.wikipedia.org/wiki/NATO_phonetic_alphabet#/media/File:FAA_Phonetic_and_Morse_Chart2.svg
    A("Alfa", "al-fah"),
    B("Bravo", "brah-voh"),
    C("Charlie", "char-lee"),
    D("Delta", "dell-tah"),
    E("Echo", "eck-oh"),
    F("Foxtrot", "foks-trot"),
    G("Golf", "golf"),
    H("Hotel", "hoh-tel"),
    I("India", "in-dee-ah"),
    J("Juliet", "jew-lee-ett"),
    K("Kilo", "key-loh"),
    L("Lima", "lee-mah"),
    M("Mike", "mike"),
    N("November", "no-vem-ber"),
    O("Oscar", "oss-cah"),
    P("Papa", "pah-pah"),
    Q("Quebec", "keh-beck"),
    R("Romeo", "row-me-oh"),
    S("Sierra", "see-air-rah"),
    T("Tango", "tang-go"),
    U("Uniform", "you-nee-form"),
    V("Victor", "vik-tah"),
    W("Whiskey", "wiss-key"),
    Y("Yankee", "yang-key"),
    Z("Zulu", "zoo-loo");

    private final String telephony;
    private final String phonicPronunciation;

    Character(String telephony, String phonicPronunciation) {
        this.telephony = telephony;
        this.phonicPronunciation = phonicPronunciation;
    }

    public String getTelephony() {
        return telephony;
    }

    public String getPhonicPronunciation() {
        return phonicPronunciation;
    }

}
