package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageSleepFunction implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        int average = (int) sessions.stream()
                .mapToLong(SleepingSession::getDuration)
                .average()
                .orElse(0);
        return new SleepAnalyticsResult("Средняя продолжительность сна в минутах: ", average);
    }
}