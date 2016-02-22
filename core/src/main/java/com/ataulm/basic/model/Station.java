package com.ataulm.basic.model;

import com.google.gson.annotations.SerializedName;

public class Station {
    @SerializedName("id")
    public final String id;

    @SerializedName("name")
    public final String name;

    public Station(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
