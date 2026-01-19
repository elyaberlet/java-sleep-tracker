package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeFinder implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    private static final LocalTime OWL_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_END = LocalTime.of(7, 0);

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        return new SleepAnalyticsResult("Ваш тип: ", findChronotype(sessions));
    }

    public static String findChronotype(List<SleepingSession> sessions) {

        if (sessions == null || sessions.isEmpty()) return Chronotype.DOVE.getRussianName();
        int owls = (int) sessions.stream()
                .filter(ChronotypeFinder::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isAfter(OWL_START) &&
                        s.getEnd().toLocalTime().isAfter(OWL_END))
                .count();

        int larks = (int) sessions.stream()
                .filter(ChronotypeFinder::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isBefore(LARK_START) &&
                        s.getEnd().toLocalTime().isBefore(LARK_END))
                .count();

        int doves = (int) sessions.stream()
                .filter(ChronotypeFinder::isNightSession)
                .count() - owls - larks;

        if (owls > larks && owls > doves) return Chronotype.OWL.getRussianName();
        if (larks > owls && larks > doves) return Chronotype.LARK.getRussianName();

        return Chronotype.DOVE.getRussianName();
    }

    private static boolean isNightSession(SleepingSession s) {
        LocalDate endDate = s.getEnd().toLocalDate();
        LocalDateTime midnight = endDate.atStartOfDay();
        LocalDateTime sixAM = endDate.atTime(6, 0);
        return s.getStart().isBefore(sixAM) && s.getEnd().isAfter(midnight);
    }
}
