package com.ataulm.basic.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LondonUnderground {
    @SerializedName("lines")
    public final List<com.ataulm.basic.model.Line> lines;

    LondonUnderground(List<com.ataulm.basic.model.Line> lines) {
        this.lines = lines;
    }
}
