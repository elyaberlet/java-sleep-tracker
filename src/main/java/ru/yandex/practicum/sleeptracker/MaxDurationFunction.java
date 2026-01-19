package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        int max = (int) sessions.stream()
                .mapToLong(SleepingSession::getDuration)
                .max()
                .orElse(0);
        return new SleepAnalyticsResult("Максимальная продолжительность сна в минутах: ", max);

    }
}