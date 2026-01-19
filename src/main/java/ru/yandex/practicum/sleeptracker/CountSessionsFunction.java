package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CountSessionsFunction implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        return new SleepAnalyticsResult("Количество сессий сна: ", sessions.size());
    }
}
