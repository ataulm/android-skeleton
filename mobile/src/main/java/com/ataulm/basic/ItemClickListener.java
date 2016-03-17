package com.ataulm.basic;

interface ItemClickListener {
    void onClick(Item item);

    void onClickSelect(Item item);

    void onClickDeselect(Item item);
}
