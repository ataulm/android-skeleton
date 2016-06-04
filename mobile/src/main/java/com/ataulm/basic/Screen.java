package com.ataulm.basic;

enum Screen {
    A("screen_a"),
    B("screen_b"),
    C("screen_c");

    private final String path;

    Screen(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
