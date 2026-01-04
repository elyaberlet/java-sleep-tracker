package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        int min = (int) sessions.stream()
                .mapToLong(SleepingSession::getDuration)
                .min()
                .orElse(0);
        return new  SleepAnalyticsResult("Минимальная продолжительность сна в минутах: ", min);

    }
}