package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualityCountFunction implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        int amount = (int) sessions.stream()
                .filter(s -> s.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalyticsResult("Количество сессий с плохим качеством сна: ", amount);
    }
}
