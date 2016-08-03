package com.ataulm.basic;

import com.crashlytics.android.answers.CustomEvent;

abstract class Event extends CustomEvent {

    protected Event(String eventName) {
        super(eventName);
    }

}
