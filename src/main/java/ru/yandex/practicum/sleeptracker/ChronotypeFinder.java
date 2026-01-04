package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeFinder implements Function<List<SleepingSession>, SleepAnalyticsResult> {
    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        return new SleepAnalyticsResult("Ваш тип: ", findChronotype(sessions));
    }

    enum Chronotype {
        СОВА, ЖАВОРОНОК, ГОЛУБЬ
    }

    public static Chronotype findChronotype(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) return Chronotype.ГОЛУБЬ;
        int owls = (int) sessions.stream()
                .filter(ChronotypeFinder::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isAfter(LocalTime.of(23, 0)) &&
                        s.getEnd().toLocalTime().isAfter(LocalTime.of(9, 0)))
                .count();

        int larks = (int) sessions.stream()
                .filter(ChronotypeFinder::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isBefore(LocalTime.of(22, 0)) &&
                        s.getEnd().toLocalTime().isBefore(LocalTime.of(7, 0)))
                .count();

        int doves = (int) sessions.stream()
                .filter(ChronotypeFinder::isNightSession)
                .count() - owls - larks;

        if (owls > larks && owls > doves) return Chronotype.СОВА;
        if (larks > owls && larks > doves) return Chronotype.ЖАВОРОНОК;

        return Chronotype.ГОЛУБЬ;
    }

    private static boolean isNightSession(SleepingSession s) {
        LocalDate endDate = s.getEnd().toLocalDate();
        LocalDateTime midnight = endDate.atStartOfDay();
        LocalDateTime sixAM = endDate.atTime(6, 0);
        return s.getStart().isBefore(sixAM) && s.getEnd().isAfter(midnight);
    }
}
