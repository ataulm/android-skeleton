package com.ataulm.basic.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Line {
    @SerializedName("id")
    public final String id;

    @SerializedName("name")
    public final String name;

    @SerializedName("stations")
    public final List<Station> stations;

    Line(String id, String name, List<Station> stations) {
        this.id = id;
        this.name = name;
        this.stations = stations;
    }
}
