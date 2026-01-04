package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class InsomniaNightsFunction implements Function<List<SleepingSession>, SleepAnalyticsResult> {

    private static final LocalTime NOON = LocalTime.NOON;

    @Override
    public SleepAnalyticsResult apply(List<SleepingSession> sessions) {
        return new SleepAnalyticsResult("Количество бессонных ночей: ", countInsomniaNights(sessions));
    }

    private int countInsomniaNights(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return 0;
        }

        List<SleepingSession> sorted = sessions.stream()
                .sorted(Comparator.comparing(SleepingSession::getStart))
                .toList();

        LocalDateTime firstStart = sorted.getFirst().getStart();
        LocalDateTime lastEnd = sorted.getLast().getEnd();

        LocalDate firstNightDate = firstStart.toLocalTime().isAfter(NOON)
                ? firstStart.toLocalDate().plusDays(1)
                : firstStart.toLocalDate();

        LocalDate lastNightDate = lastEnd.toLocalTime().isAfter(NOON)
                ? lastEnd.toLocalDate()
                : lastEnd.toLocalDate().minusDays(1);

        if (firstNightDate.isAfter(lastNightDate)) {
            return 0;
        }

        return (int) Stream.iterate(firstNightDate, d -> !d.isAfter(lastNightDate), d -> d.plusDays(1))
                .filter(date -> sessions.stream().noneMatch(s ->
                        s.getStart().isBefore(date.atTime(6, 0)) &&
                        s.getEnd().isAfter(date.atStartOfDay())
                        ))
                .count();
    }
}
