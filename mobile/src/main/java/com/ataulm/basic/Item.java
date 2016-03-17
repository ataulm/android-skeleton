package com.ataulm.basic;

class Item implements Comparable<Item> {

    public final int id;
    public final boolean selected;

    Item(int id, boolean selected) {
        this.id = id;
        this.selected = selected;
    }

    @Override
    public int compareTo(Item another) {
        if (id == another.id) {
            return 0;
        }

        if (id > another.id) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return id == item.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
