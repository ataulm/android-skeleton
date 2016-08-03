package com.ataulm.basic;

import com.crashlytics.android.answers.Answers;

class EventTracker {

    private final Answers answers;

    public static EventTracker newInstance() {
        Answers answers = Answers.getInstance();
        return new EventTracker(answers);
    }

    EventTracker(Answers answers) {
        this.answers = answers;
    }

    public void track(Event event) {
        answers.logCustom(event);
    }

}
