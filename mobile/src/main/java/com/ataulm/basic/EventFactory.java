package com.ataulm.basic;

import java.util.concurrent.TimeUnit;

class EventFactory {

    Event createUserEnabledTalkBack() {
        TalkBackEvent event = new TalkBackEvent();
        event.putCustomAttribute("enabled", String.valueOf(true));
        return event;
    }

    Event createUserDisabledTalkBack() {
        TalkBackEvent event = new TalkBackEvent();
        event.putCustomAttribute("enabled", String.valueOf(false));
        return event;
    }

    Event createAppLaunchLoadTime(long duration, TimeUnit timeUnit) {
        AppLaunchEvent event = new AppLaunchEvent();
        event.putCustomAttribute("loadTime", timeUnit.toMillis(duration));
        return event;
    }

}
