package com.ataulm.basic;

import java.util.concurrent.TimeUnit;

class Duration {
    private final long durationMillis;

    public static Duration inClosestMillisFrom(long duration, TimeUnit timeUnit) {
        long millis = timeUnit.toMillis(duration);
        return new Duration(millis);
    }

    private Duration(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Duration duration = (Duration) o;

        return durationMillis == duration.durationMillis;

    }

    @Override
    public int hashCode() {
        return (int) (durationMillis ^ (durationMillis >>> 32));
    }
}
