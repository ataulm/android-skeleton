package com.ataulm.basic.model.line;

import java.util.List;

public class Line {
    public int id;
    public String name;
    public List<Station> stations;

    public Line(int id, String name, List<Station> stations) {
        this.id = id;
        this.name = name;
        this.stations = stations;
    }
}
